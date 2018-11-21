package com.heybeach.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun dispatchOnMainThread(block: () -> Unit) {
    withContext(Dispatchers.Main) {
        block()
    }
}