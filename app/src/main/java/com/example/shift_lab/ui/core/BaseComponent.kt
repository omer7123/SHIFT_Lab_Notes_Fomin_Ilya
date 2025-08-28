package com.example.shift_lab.ui.core

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shift_lab.R
import com.example.shift_lab.ui.theme.AppTheme

@Composable
fun PlaceholderError(
    @StringRes text: Int = R.string.unknown_error_placeholder,
    onClickBtn: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_place),
            contentDescription = stringResource(id = text)
        )
        Text(
            modifier = Modifier
                .padding(top = 100.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(id = text),
            style = AppTheme.typography.bodyL
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.primaryBackground
            ),
            onClick = onClickBtn,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                stringResource(R.string.thank),
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryTextInvert
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderErrorPreview() {
    AppTheme {
        PlaceholderError(
            text = R.string.unknown_error_placeholder,
            onClickBtn = {}
        )
    }
}