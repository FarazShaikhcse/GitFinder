package com.faraz.gitfinder.ui.common

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.faraz.gitfinder.ui.theme.GitFinderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, showBackButton: Boolean,  onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigationClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            } else {
                null
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary, // Background color
            titleContentColor = MaterialTheme.colorScheme.onPrimary, // Title text color
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary // Icon color
        )
    )
}
