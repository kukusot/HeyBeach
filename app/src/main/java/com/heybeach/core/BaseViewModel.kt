package com.heybeach.core

import androidx.lifecycle.ViewModel
import com.heybeach.app.GlobalProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

    private val parentJob = Job()
    val scope = CoroutineScope(GlobalProvider.io + parentJob)

    override fun onCleared() {
        parentJob.cancel()
    }
}