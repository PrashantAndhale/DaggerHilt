package com.example.daggerhilt.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhilt.Repositories.MainRespository
import com.example.daggerhilt.Utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRespository: MainRespository
) : ViewModel() {

    val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val postStateFlow: MutableStateFlow<ApiState<Any>> = MutableStateFlow(ApiState.Empty)

    val _postStateFlow: StateFlow<ApiState<Any>> = postStateFlow


    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String>
        get() = _textLiveData

    fun getPost() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRespository.getPost().catch { e ->
            postStateFlow.value = ApiState.Failure(e)
        }.collect {
            postStateFlow.value = ApiState.Success(it)
        }

    }
}