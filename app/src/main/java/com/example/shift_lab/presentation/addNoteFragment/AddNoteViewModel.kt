package com.example.shift_lab.presentation.addNoteFragment

import androidx.compose.foundation.text.InlineTextContent
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
import java.util.Date
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
        val note = NoteCreateEntity(currState.title, currState.content, Date().toString())
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
        val formatted = formatTitle(rawTitle.take(16))

        _screenState.update { state ->
            val hasContent = formatted.isNotBlank() || state.content.isNotBlank()
            state.copy(
                title = formatted,
                visibleBtnSave = hasContent
            )
        }
    }

    fun changeContent(text: String) {
        val formatted = formatTitle(text)

        _screenState.update { state ->
            val hasContent = state.title.isNotBlank() || formatted.isNotBlank()
            state.copy(
                content = text,
                visibleBtnSave = hasContent
            )
        }
    }

    private fun formatTitle(input: String): String {
        return if (input.isNotEmpty()) {
            input[0].uppercaseChar() + input.substring(1)
        } else ""
    }
}