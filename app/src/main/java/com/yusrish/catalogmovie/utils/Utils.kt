package com.yusrish.catalogmovie.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    @Throws(ParseException::class)
    fun dateFormat(date: String?, input : String, output : String) : String{
        var format = SimpleDateFormat(input, Locale.getDefault())

        val newDate: Date? = format.parse(date ?: "")

        format = SimpleDateFormat(output, Locale.getDefault())

        return format.format(newDate!!)
    }


}