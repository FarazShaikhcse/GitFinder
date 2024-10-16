package com.faraz.gitfinder.ui.repodetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.faraz.gitfinder.util.Routes
import com.faraz.gitfinder.viewmodel.SharedViewModel

@Composable
fun ContributorsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val contributors by viewModel.contributors.collectAsState()

    LazyColumn {
        items(contributors) { contributor ->
            Row(
                modifier =
                    Modifier.padding(8.dp).clickable {
                        viewModel.fetchContributorRepositories(contributor.login)
                        navController.navigate(Routes.ROUTE_CONTRIBUTOR)
                    },
            ) {
                Image(
                    painter = rememberAsyncImagePainter(contributor.avatar_url),
                    contentDescription = "Contributor Avatar",
                    modifier = Modifier.size(40.dp),
                )
                Text(
                    text = contributor.login,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Contributions: ${contributor.contributions}",
                )
            }
        }
    }
}
