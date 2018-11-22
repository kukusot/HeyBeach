package com.heybeach.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

    val parentJob = Job()
    val scope = CoroutineScope(Dispatchers.IO + parentJob)

    override fun onCleared() {
        parentJob.cancel()
    }
}