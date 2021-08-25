package com.example.myapplication.utils

import android.text.TextUtils

object StringUtils {
    fun toUpperOptionInstrument(instrumentId: String): String {
        if (TextUtils.isEmpty(instrumentId)) {
            return ""
        }
        val chars = instrumentId.toCharArray()
        for (i in chars.indices) {
            if (chars[i] == 'c') {
                if (i != 0 && i != 1) {
                    chars[i] = 'C'
                }
            }
            if (chars[i] == 'p') {
                if (i != 0 && i != 1) {
                    chars[i] = 'P'
                }
            }
        }
        return String(chars)
    }
}