package com.example.shift_lab.ui.addNoteFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.shift_lab.R
import com.example.shift_lab.di.multiViewModelFactory.MultiViewModelFactory
import com.example.shift_lab.presentation.addNoteFragment.AddNoteScreenState
import com.example.shift_lab.presentation.addNoteFragment.AddNoteViewModel
import com.example.shift_lab.ui.core.PlaceholderError
import com.example.shift_lab.ui.homeFragment.HomeFragment
import com.example.shift_lab.ui.theme.AppTheme
import com.example.shift_lab.util.getAppComponent
import javax.inject.Inject

class AddNoteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: AddNoteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddNoteViewModel::class.java]
    }
    private var idNote: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().noteComponent().create().inject(this)
        idNote = arguments?.getInt(HomeFragment.ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idNote?.let {
            viewModel.getNoteById(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold { innerPadding ->
                    AddNoteScreen(
                        modifier = Modifier
                            .background(color = AppTheme.colors.screenBackground),
                        viewModel = viewModel,
                        navController = findNavController()
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddNoteScreen(
        modifier: Modifier = Modifier,
        viewModel: AddNoteViewModel,
        navController: NavController
    ) {
        val viewState = viewModel.screenState.collectAsState()
        val state = viewState.value

        when {
            state.successSave -> {
                navController.popBackStack()
            }

            state.loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            state.error -> {
                PlaceholderError {
                    viewModel.removeError()
                }
            }

            else -> {
                AddNoteScreenContent(
                    modifier = modifier,
                    content = viewState.value,
                    onChangeTitle = { viewModel.changeTitle(it) },
                    onChangeContent = { viewModel.changeContent(it) },
                    onSaveBtnClick = { viewModel.saveNote(idNote) },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }


    @Composable
    private fun AddNoteScreenContent(
        modifier: Modifier,
        content: AddNoteScreenState,
        onChangeTitle: (String) -> Unit,
        onChangeContent: (String) -> Unit,
        onSaveBtnClick: () -> Unit,
        onBackClick: () -> Unit,
    ) {
        val scrollState = rememberScrollState()

        Column(modifier = modifier) {
            Row {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = AppTheme.colors.primaryText
                    )
                }

                if (content.visibleBtnSave) {
                    IconButton(onClick = { onSaveBtnClick() }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done",
                            tint = AppTheme.colors.primaryBackground
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = content.title,
                    textStyle = AppTheme.typography.titleXS,
                    onValueChange = { input ->
                        onChangeTitle(input)
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    label = { Text(text = "Заголовок") },
                    maxLines = 1,
                    singleLine = true,
                    placeholder = { Text(text = "Введите заголовок...") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = content.content,
                    onValueChange = { onChangeContent(it) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppTheme.colors.primaryText,
                        unfocusedTextColor = AppTheme.colors.primaryText,
                        focusedContainerColor = AppTheme.colors.screenBackground,
                        unfocusedContainerColor = AppTheme.colors.screenBackground,
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    placeholder = { Text("Введите заметку...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .weight(1f),
                    singleLine = false,
                    maxLines = Int.MAX_VALUE
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun AddNoteScreen_Preview() {
        AppTheme {
            AddNoteScreenContent(
                modifier = Modifier,
                content = AddNoteScreenState(title = "", content = ""),
                onChangeContent = {},
                onChangeTitle = {},
                onSaveBtnClick = {},
                onBackClick = {}
            )
        }
    }
}
