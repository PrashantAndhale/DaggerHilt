package com.example.daggerhilt.ViewModel

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
import javax.inject.Named


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRespository: MainRespository,
    @Named("category") private var category: String
) : ViewModel() {
    val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val _postStateFlow = MutableStateFlow<ApiState<List<Any>>>(ApiState.Empty)
    fun getPost() {
        System.out.println(category)
        viewModelScope.launch {
            _postStateFlow.value = ApiState.Loading
            mainRespository.getPost()
                .catch { e -> _postStateFlow.value = ApiState.Failure(e) }
                .collect { data -> _postStateFlow.value = ApiState.Success(data) }
        }
    }
}
