package com.example.gardenerassistant.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class MVIViewModel<State : Any>(initialState: State) : ViewModel() {
    private val _viewModelState = MutableStateFlow(initialState)
    val viewModelState: StateFlow<State> = _viewModelState.asStateFlow()

    protected val state: State
        get() = _viewModelState.value

    protected fun reduce(block: () -> State) = _viewModelState.tryEmit(block())
}