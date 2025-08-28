package com.example.shift_lab.ui.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.shift_lab.R
import com.example.shift_lab.di.multiViewModelFactory.MultiViewModelFactory
import com.example.shift_lab.domain.entity.NoteEntityUI
import com.example.shift_lab.presentation.homeFragment.HomeScreenState
import com.example.shift_lab.presentation.homeFragment.HomeViewModel
import com.example.shift_lab.ui.core.PlaceholderError
import com.example.shift_lab.ui.theme.AppTheme
import com.example.shift_lab.util.getAppComponent
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().noteComponent().create().inject(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            containerColor = AppTheme.colors.fiveBackground,
                            onClick = {
                                findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
                            },
                        ) { Text("+") }

                    }) { innerPadding ->
                    HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        navController = findNavController()
                    )
                }
            }
        }
    }

    companion object{
        const val ID = "id"
    }
}


@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewState =viewModel.screenState.collectAsState()
    when(val state = viewState.value){
        is HomeScreenState.Content -> {
            HomeScreenContent(
                state.notes,
                modifier,
                onItemClick = {
                    navController.navigate(
                        R.id.action_homeFragment_to_addNoteFragment,
                        bundleOf(HomeFragment.ID to it)
                    )
                },
                onDeleteNote = {viewModel.deleteNote(it)})

        }
        HomeScreenState.Error -> {
            PlaceholderError {
                viewModel.getNotes()
            }
        }
        HomeScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@Composable
private fun HomeScreenContent(
    notes: List<NoteEntityUI>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onDeleteNote: (Int) -> Unit
) {
    var noteToDelete by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.notes),
            style = AppTheme.typography.titleCygreFont,
            modifier = Modifier.padding(16.dp)
        )
        if (notes.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(
                    items = notes,
                    key = { it.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        onItemClick = {onItemClick(it)},
                        onItemLongClick = { noteToDelete = it }
                    )
                }
            }
        }else{
            Text(
                text = stringResource(id = R.string.no_notes),
                style = AppTheme.typography.bodyM,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            )
        }
    }

    noteToDelete?.let { id ->
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text(text = "Удалить заметку?") },
            text = { Text(text = "Это действие нельзя отменить.") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteNote(id)
                    noteToDelete = null
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Отмена")
                }
            }
        )
    }
}
@Composable
private fun NoteItem(note: NoteEntityUI, onItemClick: (Int) -> Unit, onItemLongClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(
                color = AppTheme.colors.fiveBackground, shape = RoundedCornerShape(10.dp)
            )
            .combinedClickable(
                onClick = { onItemClick(note.id) },
                onLongClick = { onItemLongClick(note.id) }
            )
            .padding(
                top = 8.dp, start = 10.dp, end = 10.dp, bottom = 8.dp
            )
    ) {
        Text(
            text = note.title,
            style = AppTheme.typography.bodyMBold,
            color = AppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        if(note.content.isEmpty()){
            Text(
                text = stringResource(id = R.string.no_text),
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.secondaryText
            )
        }else {
            Text(
                text = note.content,
                maxLines = 3,
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText
            )
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Text(
            text = note.dateCreate,
            maxLines = 2,
            style = AppTheme.typography.bodyM,
            color = AppTheme.colors.secondaryText
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreen_Preview() {
    AppTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {

                }) { Text("+") }
            }) { innerPadding ->
//            HomeScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}