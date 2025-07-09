package com.example.trasuatuananh.util

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object Dialog {
    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        val defaultDate = Calendar.getInstance().apply {
            set(2000, Calendar.JANUARY, 1)
        }

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val formattedDate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
                onDateSelected(formattedDate)
            },
            defaultDate.get(Calendar.YEAR),
            defaultDate.get(Calendar.MONTH),
            defaultDate.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}