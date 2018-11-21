package com.heybeach.http

import java.io.InputStream

fun InputStream.readResponseAndClose(): String {
    val text = bufferedReader(Charsets.UTF_8).readText()
    close()
    return text
}