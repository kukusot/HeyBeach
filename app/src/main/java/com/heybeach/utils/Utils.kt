package com.heybeach.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater

@Suppress("DEPRECATION")
fun String?.createHtmlText(): CharSequence {
    return this?.run {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
    } ?: ""
}

fun Context.getLayoutInflater() = LayoutInflater.from(this)!!
