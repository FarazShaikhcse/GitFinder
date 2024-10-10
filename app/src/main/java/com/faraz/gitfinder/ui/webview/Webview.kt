package com.faraz.gitfinder.ui.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.faraz.gitfinder.ui.common.TopBar
import com.faraz.gitfinder.viewmodel.SharedViewModel

@Composable
fun WebViewScreen(
    viewModel: SharedViewModel,
    navController: NavHostController,
) {
    val url by viewModel.repoURL.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = "Webview",
                showBackButton = true,
                onNavigationClick = { navController.popBackStack() },
            )
        },
        content = { padding ->
            // Use AndroidView to display a WebView within Compose
            AndroidView(
                factory = {
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        loadUrl(url ?: "")
                    }
                },
                update = { webView ->
                    // Update the URL if it changes
                    url?.let {
                        webView.loadUrl(it)
                    }
                },
                modifier = Modifier.padding(padding),
            )
        },
    )
}
