package com.example.shift_lab.presentation.homeFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.useCases.GetAllNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
): ViewModel() {
    private var _screenState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val screenState: StateFlow<HomeScreenState> = _screenState

    init {
        getNotes()
    }

    fun getNotes(){
        viewModelScope.launch {
            getAllNotesUseCase().collect { status ->
                when(status){
                    is Resource.Error -> HomeScreenState.Error
                    Resource.Loading -> HomeScreenState.Loading
                    is Resource.Success -> {
                        Log.e("Date: ", "${status.data[0].dateCreate}")
                        _screenState.value = HomeScreenState.Content(status.data)
                    }
                }
            }
        }
    }
}