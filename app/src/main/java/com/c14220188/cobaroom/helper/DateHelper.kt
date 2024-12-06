package com.c14220188.cobaroom.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun getCurrentDate() : String {
        val dateFormat = SimpleDateFormat (
                "yyyy/MM/DD HH:mm:ss",
                Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }
}