import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64

object TokenManager {
    private const val alias = "TokenKeyAlias"
    private const val prefName = "SecurePrefs"
    private const val keyToken = "encryptedToken"
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    fun init(context: Context) {
        if (!keyStore.containsAlias(alias)) {
            generateKey()
        }
    }

    // Hàm lưu token vào SharedPreferences và mã hóa bằng Keystore
    fun saveToken(context: Context, token: String) {
        val encryptedData = encryptData(token.toByteArray())
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(keyToken, Base64.encodeToString(encryptedData, Base64.DEFAULT)).apply()
    }

    // Hàm lấy token từ SharedPreferences và giải mã bằng Keystore
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        val encryptedData = sharedPreferences.getString(keyToken, null) ?: return null
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        return decryptData(decodedData)
    }

    // Tạo khóa AES trong Keystore
    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keySpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(keySpec)
        keyGenerator.generateKey()
    }

    // Hàm mã hóa dữ liệu
    private fun encryptData(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data)
        return iv + encryptedData
    }

    // Hàm giải mã dữ liệu
    private fun decryptData(data: ByteArray): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val iv = data.copyOfRange(0, 12) // GCM IV có độ dài 12 byte
        val encryptedData = data.copyOfRange(12, data.size)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encryptedData))
    }

    // Lấy SecretKey từ Keystore
    private fun getSecretKey(): SecretKey {
        return (keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
    }
}
