package com.example.shift_lab.ui.addNoteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.shift_lab.ui.theme.AppTheme

class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold {innerPadding->
                    AddNoteScreen(modifier = Modifier.imePadding().padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun AddNoteScreen(modifier: Modifier = Modifier) {
        var text by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Заметка") },
                placeholder = { Text("Введите заметку...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                singleLine = false,
                maxLines = Int.MAX_VALUE
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun AddNoteScreen_Preview(){
        AppTheme {
            AddNoteScreen()
        }
    }

}