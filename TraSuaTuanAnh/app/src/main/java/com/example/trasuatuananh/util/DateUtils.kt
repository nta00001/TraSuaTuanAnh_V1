package com.example.trasuatuananh.util

import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    // Định dạng ngày trong response
    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    // Định dạng ngày bạn muốn chuyển đổi
    private val outputDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

    /**
     * Chuyển đổi ngày từ định dạng "yyyy-MM-dd'T'HH:mm:ss" sang "dd-MM-yyyy".
     * Nếu không thể parse được ngày, sẽ trả về "Invalid date".
     */
    fun formatDate(inputDate: String?): String {
        return try {
            if (inputDate.isNullOrBlank()) return ""
            // Phân tích và chuyển đổi ngày
            val parsedDate = inputDateFormat.parse(inputDate)
            parsedDate?.let {
                outputDateFormat.format(it)
            } ?: "Invalid date"
        } catch (e: ParseException) {
            // Xử lý khi không thể phân tích ngày
            e.printStackTrace()
            "Invalid date"
        }
    }

    fun parseDate(formattedDate: String): Date? {
        return try {
            outputDateFormat.parse(formattedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    // Định dạng ngày chỉ lấy "dd" cho DayValueFormatter
    fun formatDay(inputDate: String): String {
        return try {
            // Định dạng ngày đầu vào là "dd-MM-yyyy"
            val inputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val parsedDate = inputDateFormat.parse(inputDate)
            parsedDate?.let {
                // Trả về chỉ phần ngày (dd)
                SimpleDateFormat("dd", Locale.getDefault()).format(it)
            } ?: "Invalid day"
        } catch (e: ParseException) {
            e.printStackTrace()
            "Invalid day"
        }
    }

}

class DayValueFormatter(private val list: List<RevenueResponse>) : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        // Lấy ngày từ danh sách `RevenueResponse`
        val revenue = list.getOrNull(value.toInt()) ?: return ""
        return DateUtils.formatDay(revenue.date) // Chuyển đổi chỉ lấy ngày
    }
}

