package com.faraz.gitfinder.ui.contributordetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.faraz.gitfinder.ui.common.Loader
import com.faraz.gitfinder.ui.common.TopBar
import com.faraz.gitfinder.util.Routes
import com.faraz.gitfinder.viewmodel.SharedViewModel

@Composable
fun ContributorReposScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val repos by viewModel.contributorRepos.collectAsState()

    if (repos.isEmpty()) {
        Loader()
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    title = repos.first()?.owner?.login ?: "Something Went Wrong",
                    showBackButton = true,
                    onNavigationClick = { navController.popBackStack() },
                )
            },
            content = { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    repos.ifEmpty {
                    }
                    // Display repository image
                    Image(
                        painter =
                            rememberAsyncImagePainter(repos.firstOrNull()?.owner?.avatar_url ?: ""),
                        contentDescription = "Owner Avatar",
                        modifier =
                            Modifier
                                .size(120.dp)
                                .padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally),
                    )

                    Text(
                        text = repos.first().owner.login,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Repositories:",
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(repos) { repo ->
                            Text(
                                text = repo.name,
                                color = Color.Blue,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable {
                                            viewModel.setURLToLoad(repo.html_url)
                                            navController.navigate(
                                                Routes.ROUTE_WEBVIEW,
                                            )
                                        },
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            },
        )
    }
}
