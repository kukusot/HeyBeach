package com.heybeach.utils

import com.heybeach.app.GlobalProvider
import kotlinx.coroutines.withContext

suspend fun dispatchOnMainThread(block: () -> Unit) {
    withContext(GlobalProvider.main) {
        block()
    }
}