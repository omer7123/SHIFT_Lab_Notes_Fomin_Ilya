package com.example.shift_lab.presentation.addNoteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.useCases.CreateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val useCase: CreateNoteUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<AddNoteScreenState>(
        AddNoteScreenState()
    )

    val screenState: StateFlow<AddNoteScreenState> = _screenState.asStateFlow()

    fun saveNote(){
        val currState = screenState.value
        val note = NoteCreateEntity(currState.title, currState.content, ZonedDateTime.now())
        viewModelScope.launch {
            useCase(note).collect {state ->
                when(state) {
                    is Resource.Error -> _screenState.update {
                        it.copy(error = true)
                    }
                    Resource.Loading -> _screenState.update {
                        it.copy(
                            loading = true
                        )
                    }
                    is Resource.Success -> {
                        _screenState.update {
                            it.copy(
                                successSave = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun removeError() {
        _screenState.update {
            it.copy(error = false)
        }
    }

    fun changeTitle(rawTitle: String) {

        _screenState.update { state ->
            val hasContent = rawTitle.isNotBlank() || state.content.isNotBlank()
            state.copy(
                title = rawTitle,
                visibleBtnSave = hasContent
            )
        }
    }

    fun changeContent(text: String) {

        _screenState.update { state ->
            val hasContent = state.title.isNotBlank() || text.isNotBlank()
            state.copy(
                content = text,
                visibleBtnSave = hasContent
            )
        }
    }
}