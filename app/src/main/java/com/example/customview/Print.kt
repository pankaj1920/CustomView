package com.example.customview

import android.util.Log

object Print {
    fun log(message: String?) {
//        if (BuildConfig.DEBUG) {
            println("<---5MTP--->")
            Log.e("Print",": $message")
//        }
    }
}