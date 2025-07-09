package com.example.trasuatuananh.util

import android.widget.EditText
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.google.gson.Gson
import java.util.regex.Pattern
import java.text.DecimalFormat
import java.lang.reflect.Type
import java.text.Normalizer

object StringUtils {
    private val ACCENTED_MAP: Map<Char, String> = createAccentedMap()
    private val NUMBER_FORMAT2 = DecimalFormat("#,###")
    private val gson: Gson = Gson()


    fun createAccentedMap(): Map<Char, String> {
        val accentedMap = mutableMapOf<Char, String>()
        val accents = charArrayOf(
            'á',
            'à',
            'ạ',
            'ã',
            'ả',
            'ă',
            'â',
            'đ',
            'é',
            'è',
            'ẻ',
            'ẽ',
            'ẹ',
            'ê',
            'í',
            'ì',
            'ĩ',
            'ỉ',
            'ị',
            'ò',
            'ó',
            'ỏ',
            'õ',
            'ọ',
            'ô',
            'ơ',
            'ù',
            'ú',
            'ủ',
            'ũ',
            'ụ',
            'ư',
            'ỳ',
            'ý',
            'ỷ',
            'ỹ',
            'ỵ',
            'Á',
            'À',
            'Ạ',
            'Ã',
            'Ả',
            'Ă',
            'Â',
            'Đ',
            'É',
            'È',
            'Ẻ',
            'Ẽ',
            'Ẹ',
            'Ê',
            'Í',
            'Ì',
            'Ĩ',
            'Ỉ',
            'Ị',
            'Ò',
            'Ó',
            'Ỏ',
            'Õ',
            'Ọ',
            'Ô',
            'Ơ',
            'Ù',
            'Ú',
            'Ủ',
            'Ũ',
            'Ụ',
            'Ư',
            'Ỳ',
            'Ý',
            'Ỷ',
            'Ỹ',
            'Ỵ'
        )

        val replacements = arrayOf(
            "as",
            "af",
            "aj",
            "ax",
            "ar",
            "aw",
            "aa",
            "dd",
            "es",
            "ef",
            "er",
            "ex",
            "ej",
            "ee",
            "is",
            "if",
            "ix",
            "ir",
            "ij",
            "of",
            "os",
            "or",
            "ox",
            "oj",
            "oo",
            "ow",
            "uf",
            "us",
            "ur",
            "ux",
            "uj",
            "uw",
            "yf",
            "ys",
            "yr",
            "yx",
            "yj",
            "AS",
            "AF",
            "AJ",
            "AX",
            "AR",
            "AW",
            "AA",
            "DD",
            "ES",
            "EF",
            "ER",
            "EX",
            "EJ",
            "EE",
            "IS",
            "IF",
            "IX",
            "IR",
            "IJ",
            "OF",
            "OS",
            "OR",
            "OX",
            "OJ",
            "OO",
            "OW",
            "UF",
            "US",
            "UR",
            "UX",
            "UJ",
            "UW",
            "YF",
            "YS",
            "YR",
            "YX",
            "YJ"
        )

        for (i in accents.indices) {
            accentedMap[accents[i]] = replacements[i]
        }
        return accentedMap
    }


    fun removeEdittextInputAccent(editText: EditText, input: String?, oldText: String): String {
        return try {
            var newString = ""
            val cursorPosition = editText.selectionStart
            if (!input.isNullOrEmpty()) {
                newString = if (input.length - oldText.length > 1) {
                    StringUtils.removeVNUniCode(VNCharacterUtils.removeAccent2(input), true)
                } else {
                    StringUtils.removeVNUniCode(input, true)
                }

                if (editText.text.toString() != newString) {
                    editText.setText(newString)
                    editText.setSelection(cursorPosition + (newString.length - input.length))
                }
            }
            editText.text.toString()
        } catch (ex: Exception) {
            ""
        }
    }

    fun removeVNUniCode(input: String, removeSpecialChar: Boolean): String {
        val newString = StringBuilder()
        val pattern = Pattern.compile("[^a-zA-Z0-9,\\s.\\-_]")

        for (char in input) {
            var newLastText = replaceUniCodeToText(char)
            if (removeSpecialChar) {
                if (pattern.matcher(newLastText).find()) {
                    newLastText = newLastText.replace("[^a-zA-Z0-9,\\s.\\-_]".toRegex(), "")
                }
            }
            newString.append(newLastText)
        }
        return newString.toString()
    }

    fun formatMoney(value: String): String {
        return formatMoney(value, returnOriginIfEmptyValue = false)
    }

    fun formatMoney(value: Int): String {
        return formatMoney(value.toString())
    }


    fun formatMoney(value: Double): String {
        return formatMoney(value.toString())
    }

    fun formatMoney(value: String, returnOriginIfEmptyValue: Boolean): String {
        if (returnOriginIfEmptyValue && value.isEmpty()) return value
        return try {
            NUMBER_FORMAT2.format(parseDouble(value))
        } catch (e: Exception) {
            ""
        }
    }

    fun parseDouble(value: String): Double {
        return try {
            value.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }


    fun replaceUniCodeToText(lastText: Char): String {
        val replaceString = ACCENTED_MAP[lastText]
        return replaceString ?: lastText.toString()
    }

    fun isValidLength(input: String): Boolean {
        return input.length in 6..32
    }

    fun isSameString(input: String, output: String): Boolean {
        return input == output
    }

    fun isValidPhoneNumber(input: String): Boolean {
        val validCountryCodes = setOf("+84", "84", "0")
        return validCountryCodes.any { input.startsWith(it) } &&
                input.replace("[^0-9+]".toRegex(), "").length == 10
    }

    fun getSizeString(size: String?): String {
        return if (size.isNullOrEmpty()) "" else "Size $size"
    }

    fun objectToString(obj: Any?): String {
        return gson.toJson(obj)
    }

    fun <T> stringToObject(json: String, classType: Type): T {
        return gson.fromJson(json, classType)
    }

    fun formatTotalPayment(value: String): String =
        "Tổng tiền: $value đ"


    fun calculateTotalPrice(listFood: List<Food>): String {
        val totalPrice = listFood.sumOf { tea ->
            val quantity = tea.quantity
            val price = tea.price
            val type = tea.size

            when (type) {
                "M" -> price * quantity
                "L" -> (price + 5000.0) * quantity
                else -> 0.0
            }
        }
        return formatMoney(totalPrice)
    }

    fun calculateItemPrice(tea: Food): String {
        val extra = if (tea.size == "L") 5000.0 else 0.0
        val itemPrice = (tea.price + extra) * tea.quantity
        return formatMoney(itemPrice)
    }



    fun appendCurrencySymbol(value: String): String {
        return "$value đ"
    }


    fun parseMoney(value: String?): Int {
        return value?.replace("[^0-9]".toRegex(), "")?.toIntOrNull() ?: 0
    }

    fun removeCommasAndDots(input: String): Int {
        val cleanedString = input.replace("[,\\.]".toRegex(), "")
        return cleanedString.toIntOrNull() ?: 0  // Nếu không chuyển đổi được, trả về 0
    }

    fun calculateDiscountAmount(totalStr: String, discountStr: String, percentStr: String): String {
        return try {
            val total = parseMoney(totalStr)
            val discount = parseMoney(discountStr)
            val percent = parseMoney(percentStr)
            val percentValue = total * percent / 100

            formatMoney(if (discount > percentValue) percentValue else discount)
        } catch (e: NumberFormatException) {
            "Invalid input: one or more strings are not valid numbers."
        }
    }


    fun calculateTotalFromStrings(amounts: List<RevenueResponse>): String {
        return formatMoney(amounts.sumOf { it.tien.toDoubleOrNull() ?: 0.0 }) + "đ"
    }

    fun fixVietnameseCharacters(input: String): String {
        // Normalize để chuẩn hóa ký tự Unicode
        return Normalizer.normalize(input, Normalizer.Form.NFKC)
            .replace("?", "đ") // Thay thế "?" nếu cần
    }

    fun formatMoneyClean(value: String): Double {
        val cleanedValue = value.replace(".", "")
        return cleanedValue.toDoubleOrNull() ?: 0.0
    }



}