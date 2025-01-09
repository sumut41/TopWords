package com.skyvo.mobile.core.base.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseComposeViewModel<S> : BaseViewModel() {
    private val initialState: S by lazy { setInitialState() }

    abstract fun setInitialState(): S

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    protected fun setState(state: S.() -> S) = viewModelScope.launch {
        _state.update(state)
    }

    fun clearState() = viewModelScope.launch {
        _state.update {
            initialState
        }
    }
}