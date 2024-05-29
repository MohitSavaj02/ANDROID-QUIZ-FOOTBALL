package com.example.baseproject.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.app.BWApp
import com.google.gson.Gson

fun after(milliSeconds: Long, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ action() }, milliSeconds)
}

fun Int.asString(context: Context? = null): String {
    if (context != null) return context.getString(this)
    return BWApp.getAppInstance().getString(this)
}

fun Int.asString(context: Context? = null, arg: String?): String {
    if (context != null) return context.getString(this)
    return BWApp.getAppInstance().getString(this, arg)
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun ImageView.load(data: Any?) {
    if (data is Int) {
        this.setImageResource(data)
    } else {
        Glide.with(BWApp.getAppInstance()).load(data).placeholder(R.color.placeHolder).into(this)
    }
}

fun Int.asColor(): Int {
    return BWApp.getAppInstance().getColor(this)
}

fun Any?.toJsonString(): String {
    if (this == null) return ""
    return Gson().toJson(this) ?: this.toString()
}