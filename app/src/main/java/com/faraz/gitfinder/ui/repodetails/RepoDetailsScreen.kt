package com.faraz.gitfinder.ui.repodetails

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.faraz.gitfinder.data.model.GithubRepository
import com.faraz.gitfinder.ui.common.TopBar
import com.faraz.gitfinder.viewmodel.SharedViewModel

@Composable
fun RepoDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController
) {
    val repo by viewModel.selectedRepo.collectAsState()

    repo?.let { repo ->
        LaunchedEffect(key1 = Unit) {
            viewModel.fetchContributors(repo.owner?.login.toString(), repo.name.toString())
        }
        Scaffold(
            topBar = {
                TopBar(
                    title = repo.name,
                    showBackButton = true,
                    onNavigationClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Display repository image
                    Image(
                        painter = rememberImagePainter(repo.owner?.avatar_url ?: ""),
                        contentDescription = "Owner Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    // Display repository name
                    Text(
                        text = repo.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    // Display project link
                    Text(
                        text = buildAnnotatedString {
                            append("Project Link: ")
                            withStyle(style = SpanStyle(color = Color.Blue)) {
                                append(repo.htmlUrl)
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                viewModel.setURLToLoad(repo.htmlUrl)
                                navController.navigate(
                                    "webView/"
                                )
                            } // Function to open web view
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Display repository description
                    Text(
                        text = "Description:\n ${repo.description ?: "No description available."}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Contributors:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )

                    ContributorsScreen(viewModel = viewModel)
                }
            }
        )
    }
}


