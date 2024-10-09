package com.faraz.gitfinder.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faraz.gitfinder.data.model.GithubRepository
import com.faraz.gitfinder.ui.common.TopBar
import com.faraz.gitfinder.viewmodel.SharedViewModel


@Composable
fun HomeScreen(
    viewModel: SharedViewModel, // List of repositories from ViewModel
    onSearch: (String) -> Unit // Callback to handle search
) {
    var query by remember { mutableStateOf("") }

    val repositories by viewModel.repositories.collectAsState()

    Scaffold(
        topBar = {
            TopBar(title = "GitHub Repositories", showBackButton = false)
        },
        content = { _ ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                // Search Bar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            onSearch(query) // Call the search function whenever the query changes
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Black)
                            .padding(8.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Repository List
                LazyColumn {
                    items(repositories) { repository ->
                        RepositoryItem(repository)
                    }
                }
            }
        })
}

@Composable
fun RepositoryItem(repository: GithubRepository) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repository.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stars: ${repository.stargazers_count}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
