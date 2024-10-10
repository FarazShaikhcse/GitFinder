package com.faraz.gitfinder.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.faraz.gitfinder.data.db.GithubRepositoryEntity
import com.faraz.gitfinder.ui.common.Loader
import com.faraz.gitfinder.ui.common.TopBar
import com.faraz.gitfinder.util.Routes
import com.faraz.gitfinder.viewmodel.SharedViewModel

@Composable
fun HomeScreen(
    viewModel: SharedViewModel,
    navController: NavHostController,
    onSearch: (String) -> Unit, // Callback to handle search
) {
    var query by remember { mutableStateOf("") }

    val repositories by viewModel.repositories.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopBar(title = "GitHub Repositories", showBackButton = false)
        },
        content = { padding ->
            if (isLoading) {
                Loader()
            } else {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                ) {
                    errorMessage?.let {
                        Text("Error: $it")
                    }
                    // Search Bar
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        TextField(
                            value = query,
                            onValueChange = {
                                query = it
                                onSearch(query) // Call the search function whenever the query changes
                            },
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                            placeholder = { Text("Search Repository...") },
                            colors =
                                TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                ),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Repository List
                    LazyColumn {
                        itemsIndexed(repositories) { index, repository ->
                            RepositoryItem(
                                repository,
                                navController,
                                viewModel,
                            )

                            // Load the next page when the user scrolls to the end
                            if (index == repositories.size - 1) {
                                viewModel.loadNextPage(query)
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun RepositoryItem(
    repository: GithubRepositoryEntity,
    navController: NavHostController,
    viewModel: SharedViewModel,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                    viewModel.setSelectedRepo(repository)
                    navController.navigate(Routes.ROUTE_REPO_DETAILS)
                },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repository.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repository.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stars: ${repository.stargazersCount}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
