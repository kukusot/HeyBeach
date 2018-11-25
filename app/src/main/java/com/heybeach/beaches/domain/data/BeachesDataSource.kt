package com.heybeach.beaches.domain.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.heybeach.app.GlobalProvider
import com.heybeach.beaches.domain.data.NetworkState.*
import com.heybeach.beaches.domain.model.Beach
import com.heybeach.http.Response
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BeachesDataSource(private val remoteDataSource: BeachesRemoteDataSource) :
    PageKeyedDataSource<Int, Beach>() {

    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val parentJob = Job()
    private val scope = CoroutineScope(GlobalProvider.io + parentJob)
    private val _networkState = MutableLiveData<NetworkState>()

    private var retryFun: (() -> Any)? = null

    fun retry() {
        val prevRetry = retryFun
        retryFun = null
        prevRetry?.invoke()
    }

    fun clear() {
        parentJob.cancel()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Beach>) {
        scope.launch {
            postNetworkState(LOADING)
            val response = remoteDataSource.fetchBeaches(0)

            if (response is Response.Success) {
                retryFun = null
                dispatchOnMainThread {
                    _networkState.value = SUCCESS
                    callback.onResult(response.data, null, 1)
                }
            } else {
                retryFun = {
                    loadInitial(params, callback)
                }
                postNetworkState(ERROR)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Beach>) {
        scope.launch {
            postNetworkState(LOADING)
            val response = remoteDataSource.fetchBeaches(params.key)

            if (response is Response.Success) {
                retryFun = null
                dispatchOnMainThread {
                    _networkState.value = SUCCESS
                    callback.onResult(response.data, params.key + 1)
                }
            } else {
                retryFun = {
                    loadAfter(params, callback)
                }
                postNetworkState(ERROR)
            }
        }
    }

    private suspend fun postNetworkState(state: NetworkState) = dispatchOnMainThread {
        _networkState.value = state
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Beach>) {
    }

}