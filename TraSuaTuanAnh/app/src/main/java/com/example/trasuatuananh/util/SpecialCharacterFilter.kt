package com.example.trasuatuananh.util

import android.text.InputFilter
import android.text.Spanned

class SpecialCharacterFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) {
            return null
        }

        val builder = StringBuilder()
        for (i in start until end) {
            val char = source[i]
            if (char.isLetterOrDigit()) {
                builder.append(char)
            }
        }

        return builder.toString()
    }
}

class SpecialExceptSpaceCharacterFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) {
            return null
        }

        val builder = StringBuilder()
        for (i in start until end) {
            val char = source[i]
            if (char.isLetter() || char == ' ') {
                builder.append(char)
            }
        }

        return builder.toString()
    }
}

class SpecialExceptNumberAndSpaceCharacterFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source == null) {
            return null
        }

        val builder = StringBuilder()
        for (i in start until end) {
            val char = source[i]
            if (char.isLetterOrDigit() || char == ' ') {
                builder.append(char)
            }
        }

        return builder.toString()
    }
}