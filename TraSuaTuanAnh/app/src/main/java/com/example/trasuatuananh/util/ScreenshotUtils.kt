package com.example.trasuatuananh.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import java.io.File
import java.io.FileOutputStream

object ScreenshotUtils {

    /**
     * Lưu ảnh chụp màn hình từ View vào bộ nhớ
     * @param context Context của ứng dụng
     * @param view View cần chụp màn hình
     * @return Đường dẫn tệp đã lưu hoặc null nếu xảy ra lỗi
     */
    fun saveViewAsImage(context: Context, view: View): String? {
        return try {
            val bitmap = generateBitmap(view)
            saveBitmapToStorage(context, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Chuyển View thành Bitmap
     * @param view View cần chuyển
     * @return Bitmap được tạo
     */
    private fun generateBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        bgDrawable?.draw(canvas) ?: canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    /**
     * Lưu Bitmap vào bộ nhớ thiết bị
     * @param context Context của ứng dụng
     * @param bitmap Bitmap cần lưu
     * @return Đường dẫn tệp đã lưu hoặc null nếu xảy ra lỗi
     */
    private fun saveBitmapToStorage(context: Context, bitmap: Bitmap): String? {
        val fileName = "screenshot_${System.currentTimeMillis()}.png"
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Lưu ảnh vào thư mục công cộng qua MediaStore
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Screenshots")
                }
                val uri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                uri?.let {
                    context.contentResolver.openOutputStream(it).use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    }
                    uri.toString() // Trả về URI của ảnh
                }
            } else {
                // Lưu ảnh vào thư mục công cộng trên Android 9 trở xuống
                val directory = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Screenshots"
                )
                if (!directory.exists()) {
                    directory.mkdirs()
                }

                val file = File(directory, fileName)
                FileOutputStream(file).use { fos ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                }

                // Gửi broadcast để cập nhật vào Bộ sưu tập
                val filePath = file.absolutePath
                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                intent.data = Uri.fromFile(file)
                context.sendBroadcast(intent)

                filePath // Trả về đường dẫn file
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}
