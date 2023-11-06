package com.example.daggerhilt.viewmodelfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daggerhilt.Repositories.MainRespository
import com.example.daggerhilt.ViewModel.MainViewModel
import javax.inject.Inject
import javax.inject.Named


class MainViewModelFactory @Inject constructor(
    private val mainRepository: MainRespository,
    @Named("category") private var category: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(mainRepository, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

