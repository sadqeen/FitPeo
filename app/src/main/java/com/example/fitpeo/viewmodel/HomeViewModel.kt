package com.example.fitpeo.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import com.example.fitpeo.model.ResponseData
import com.example.fitpeo.network.NetworkResult
import com.example.fitpeo.respository.MainRepository
import com.example.fitpeo.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel  @Inject constructor(
    @Named("IO") private var ioDispatcher: CoroutineDispatcher,
    @Named("MAIN") private var mainDispatcher: CoroutineDispatcher,
    private val repository: MainRepository,
    @ApplicationContext private val context: Context
) : ViewModel(), LifecycleOwner {
    @SuppressLint("StaticFieldLeak")
    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }
    private var dataList =
        SingleLiveEvent<NetworkResult<List<ResponseData>>>()

    fun dataResponse(): SingleLiveEvent<NetworkResult<List<ResponseData>>> {
        return dataList
    }
    fun getData()
    {
        dataList.postValue(NetworkResult.loading(null))
        var byCategoryResponse: NetworkResult<List<ResponseData>>? = null
        viewModelScope.launch {
            withContext(ioDispatcher) {
                byCategoryResponse = repository.getDataList()
            }
            withContext(mainDispatcher) {
                byCategoryResponse?.let {
                    dataList.postValue(it)
                }
            }
        }
    }
    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override fun onCleared() {
        super.onCleared()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}