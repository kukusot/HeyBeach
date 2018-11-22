package com.heybeach.beaches.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heybeach.beaches.domain.data.BeachesRemoteDataSource
import com.heybeach.beaches.domain.data.BeachesRepository
import com.heybeach.beaches.domain.model.Beach
import com.heybeach.core.BaseViewModel
import com.heybeach.http.Response
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BeachesViewModel(private val repository: BeachesRepository) : BaseViewModel() {


    private var _beaches = MutableLiveData<Response<List<Beach>>>()
    val beaches: LiveData<Response<List<Beach>>>
        get() = _beaches

    init {
        scope.launch {
            val response = repository.getBeaches()
            dispatchOnMainThread {
                _beaches.value = response
            }
        }
    }

}