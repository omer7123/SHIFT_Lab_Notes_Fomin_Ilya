package com.example.shift_lab.presentation.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteEntityUI
import com.example.shift_lab.domain.useCases.GetAllNotesUseCase
import com.example.shift_lab.util.formatDate
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
                    is Resource.Error -> _screenState.value = HomeScreenState.Error
                    Resource.Loading -> _screenState.value = HomeScreenState.Loading
                    is Resource.Success -> {
                        _screenState.value = HomeScreenState.Content(status.data.map {
                            NoteEntityUI(it.id, it.title, it.content, formatDate(it.dateCreate))
                        })
                    }
                }
            }
        }
    }
}
