package com.faraz.gitfinder.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faraz.gitfinder.ui.contributordetails.ContributorReposScreen
import com.faraz.gitfinder.ui.home.HomeScreen
import com.faraz.gitfinder.ui.repodetails.RepoDetailsScreen
import com.faraz.gitfinder.ui.webview.WebViewScreen
import com.faraz.gitfinder.util.Routes
import com.faraz.gitfinder.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadSavedRepos()
        enableEdgeToEdge()
        setContent {
            GitFinderApp(viewModel)
        }
    }
}

@Composable
fun GitFinderApp(viewModel: SharedViewModel) {
    // Create a NavController to manage navigation between screens
    val navController = rememberNavController()

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Set up the NavHost with navigation routes
            NavHost(
                navController = navController,
                startDestination = Routes.ROUTE_HOME, // Set home screen as the start destination
            ) {
                // Home screen route
                composable(Routes.ROUTE_HOME) {
                    HomeScreen(viewModel = viewModel, navController = navController) {
                        viewModel.onSearchQueryChanged(it)
                    }
                }
                // Repo details screen route
                composable(Routes.ROUTE_REPO_DETAILS) {
                    RepoDetailsScreen(viewModel = viewModel, navController = navController)
                }
                composable(Routes.ROUTE_WEBVIEW) {
                    WebViewScreen(viewModel = viewModel, navController = navController)
                }
                // contributor details screen
                composable(Routes.ROUTE_CONTRIBUTOR) {
                    ContributorReposScreen(viewModel = viewModel, navController = navController)
                }
            }
        }
    }
}
