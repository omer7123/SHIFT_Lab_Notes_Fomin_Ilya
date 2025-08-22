package com.example.shift_lab.ui.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shift_lab.R
import com.example.shift_lab.di.multiViewModelFactory.MultiViewModelFactory
import com.example.shift_lab.domain.entity.NoteEntity
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
                            },
                        ) { Text("+") }

                    }) { innerPadding ->
                    HomeScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }

        }
    }
}

@Composable
private fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier = Modifier) {
    val viewState =viewModel.screenState.collectAsState()
    when(val state = viewState.value){
        is HomeScreenState.Content -> {
            HomeScreenContent(state.notes, modifier)
        }
        HomeScreenState.Error -> {
            PlaceholderError()
        }
        HomeScreenState.Loading -> {

        }
    }

}

@Composable
private fun HomeScreenContent(notes: List<NoteEntity>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.notes),
            style = AppTheme.typography.titleCygreFont,
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(notes) { note ->
                NoteItem(note) {

                }
            }
        }
    }
}
@Composable
private fun NoteItem(note: NoteEntity, onItemClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(
                color = AppTheme.colors.fiveBackground, shape = RoundedCornerShape(10.dp)
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

        Text(
            text = note.content,
            maxLines = 3,
            style = AppTheme.typography.bodyM,
            color = AppTheme.colors.primaryText
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