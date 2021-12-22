package com.example.gardenerassistant.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.states.DataState
import com.example.gardenerassistant.states.HomeScreenState
import com.example.gardenerassistant.usecase.FetchAllPlantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val fetchAllPlantsUseCase: FetchAllPlantsUseCase) :
    ViewModel() {
    private val _dataState = mutableStateOf(HomeScreenState())
    val dataState: State<HomeScreenState>
        get() = _dataState

    init {
        fetchAllPlants()
    }

    private fun fetchAllPlants() {
        viewModelScope.launch {
            fetchAllPlantsUseCase().onEach { result ->
                when (result) {
                    is DataState.Loading -> {
                        _dataState.value = HomeScreenState(isLoading = true)
                    }
                    is DataState.Success -> {
                        _dataState.value = HomeScreenState(data = result.data ?: emptyList())
                    }
                    is DataState.Error -> {
                        _dataState.value = HomeScreenState(error = result.message ?: "Error")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}