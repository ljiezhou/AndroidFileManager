package com.zhou.androidfilebrowser

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * Created by 周利杰 on 2017/6/7.
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resources.getString(id), length).show()
}

fun Context.getColor2(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}
