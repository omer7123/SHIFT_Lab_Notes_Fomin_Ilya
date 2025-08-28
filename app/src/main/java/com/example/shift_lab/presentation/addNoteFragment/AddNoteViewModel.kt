package com.example.shift_lab.presentation.addNoteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.entity.NoteEntity
import com.example.shift_lab.domain.useCases.CreateNoteUseCase
import com.example.shift_lab.domain.useCases.GetDraftUseCase
import com.example.shift_lab.domain.useCases.GetNoteByIdUseCase
import com.example.shift_lab.domain.useCases.RemoveDraftUseCase
import com.example.shift_lab.domain.useCases.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getDraftUseCase: GetDraftUseCase,
    private val removeDraftUseCase: RemoveDraftUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow(
        AddNoteScreenState()
    )

    val screenState: StateFlow<AddNoteScreenState> = _screenState.asStateFlow()

    private var idNoteGl: Int? = null

    init {
        getDraft()
    }


    private fun getDraft() {
        viewModelScope.launch {
            getDraftUseCase().collect { state ->

                when(state){
                    is Resource.Error ->{
                        if (state.data == null && state.msg == "Не найдено") {
                            _screenState.update {
                                it.copy(
                                    loading = false,
                                    error = false,
                                )
                            }

                        }else _screenState.update { it.copy(error = true) }
                    }
                    Resource.Loading -> _screenState.update { it.copy(loading = true) }
                    is Resource.Success -> {
                        state.data?.let { note->
                            idNoteGl = note.id
                            _screenState.update { it ->
                                it.copy(
                                    title = note.title,
                                    content = note.content,
                                    loading = false,
                                    draft = true,
                                    error = false,
                                    visibleBtnSave = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun saveNote(idNote: Int?) {
        if (idNoteGl != null){
            viewModelScope.launch {
                removeDraftUseCase(idNoteGl!!).collect{state->
                    when(state){
                        is Resource.Error -> {
                            _screenState.update { it.copy(error = true) }
                        }
                        Resource.Loading -> {
                            _screenState.update { it.copy(loading = true) }
                        }
                        is Resource.Success -> {
                            _screenState.update {
                                it.copy(successSave = true)
                            }
                        }
                    }
                }
            }
        }
        else if (idNote != null) {
            updateNote(idNote, false)
        } else {
            val currState = screenState.value
            val note = NoteCreateEntity(currState.title, currState.content, ZonedDateTime.now())

            createNewNote(note)
        }
    }

    fun saveDraft(idNote: Int?) {
        if (idNote != null) {
            _screenState.update { state ->
                state.copy(
                    successSave = true
                )
            }
            return
        }

        if(!_screenState.value.draft){
            val currState = screenState.value
            val note = NoteCreateEntity(
                    currState.title,
                    currState.content,
                    ZonedDateTime.now(),
                    true
            )
            createNewNote(note)
        }else{
            idNoteGl?.let {
                updateNote(it, true)
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

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            getNoteByIdUseCase(id).collect { state ->
                when (state) {
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
                                title = state.data.title,
                                content = state.data.content,
                                loading = false,
                                error = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateNote(idNote: Int, draft: Boolean) {
        val currState = screenState.value
        val note = NoteEntity(idNote,currState.title, currState.content, ZonedDateTime.now(), draft)

        viewModelScope.launch {
            updateNoteUseCase(note).collect { state->
                when(state){
                    is Resource.Error -> _screenState.update { it.copy(error = true) }
                    Resource.Loading -> _screenState.update { it.copy(loading = true) }
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

    private fun createNewNote(note: NoteCreateEntity) {
        viewModelScope.launch {
            createNoteUseCase(note).collect { state ->
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
}