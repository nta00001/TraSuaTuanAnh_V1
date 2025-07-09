package com.example.trasuatuananh.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

object QrUtils {

    /**
     * Tạo chuỗi QR theo chuẩn VietQR từ các thông tin ngân hàng và giao dịch.
     *
     * @param bankId Mã ngân hàng (VD: "970415" cho VietinBank).
     * @param accountNumber Số tài khoản ngân hàng.
     * @param description Nội dung giao dịch (VD: "dat don").
     * @param amount Số tiền giao dịch (VD: "50000").
     * @return Chuỗi QR hoàn chỉnh hoặc `null` nếu thông tin không hợp lệ.
     */
    fun renderQr(
        bankId: String?,
        accountNumber: String?,
        description: String,
        amount: String
    ): String? {
        if (bankId.isNullOrBlank() || accountNumber.isNullOrBlank()) return null

        val methodSend = "QRIBFTTA"
        val numberLength = accountNumber.length.toString().padStart(2, '0')
        val personLength = bankId.length + accountNumber.length + methodSend.length

        val person =
            "0010A00000072701${personLength}000${bankId.length}${bankId}01${numberLength}${accountNumber}020${methodSend.length}${methodSend}"
        val contentLength = description.length.toString().padStart(2, '0')
        val information = "08${contentLength}${description}"
        val moneyLength = amount.length.toString().padStart(2, '0')

        val qrCore =
            "00020101021238${person.length}${person}530370454${moneyLength}${amount}5802VN62${information.length}${information}6304"
        val crc = crc16(qrCore).toString(16).padStart(4, '0')

        return (qrCore + crc).uppercase()
    }

    /**
     * Tính CRC16.
     *
     * @param input Chuỗi đầu vào.
     * @return Giá trị CRC16.
     */
    private fun crc16(input: String): Int {
        var crc = 0xFFFF
        input.toByteArray().forEach { byte ->
            crc = crc xor (byte.toInt() and 0xFF)
            repeat(8) {
                crc = if ((crc and 0x1) != 0) {
                    crc shr 1 xor 0xA001
                } else {
                    crc shr 1
                }
            }
        }
        return crc and 0xFFFF
    }

    /**
     * Tạo hình ảnh QR bằng ZXing.
     *
     * @param content Nội dung QR code.
     * @return Bitmap hình ảnh QR code.
     */
    fun generateQRCode(content: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300)
        val width = bitMatrix.width
        val height = bitMatrix.height

        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}
