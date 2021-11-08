package com.example.diplomstrava.utils

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}

fun <T : Activity> T.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//fun meterToKm(merers: Double): String{
//    return "${merers/1000.0} km"
//}