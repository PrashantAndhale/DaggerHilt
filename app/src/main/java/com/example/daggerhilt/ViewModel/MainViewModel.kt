package com.example.daggerhilt.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerhilt.Model.GlobalMarketData
import com.example.daggerhilt.Model.MarketStatus
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

    val _postStateFlow = MutableStateFlow<ApiState<GlobalMarketData>>(ApiState.Empty)
    val _MarketStatusStateFlow = MutableStateFlow<ApiState<MarketStatus>>(ApiState.Empty)
    fun getGlobalIndicatesListingData(url: String, view: String, deviceType: String) {
        viewModelScope.launch {
            _postStateFlow.value = ApiState.Loading
            mainRespository.getGlobalIndicatesListingData(url, view, deviceType)
                .catch { e -> _postStateFlow.value = ApiState.Failure(e) }
                .collect { data -> _postStateFlow.value = ApiState.Success(data) }
        }
    }

    fun getMarketStatus(url: String) {
        viewModelScope.launch {
            _MarketStatusStateFlow.value = ApiState.Loading
            mainRespository.getMarketStatus(url)
                .catch { e ->
                    _MarketStatusStateFlow.value = ApiState.Failure(e)
                }
                .collect { data ->
                    _MarketStatusStateFlow.value = ApiState.Success(data)
                }
        }
    }
}
