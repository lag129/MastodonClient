package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.lag129.mastodon.ui.components.PostContent
import net.lag129.mastodon.ui.components.RepostContent
import net.lag129.mastodon.viewmodel.DataViewModel

@Composable
fun TimelineScreen(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    val statuses by viewModel.statuses.collectAsState()
    val isLoading by viewModel.isLoading

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        items(
            items = statuses,
            key = { status -> status.id },
        ) { status ->
            if (status.reblog == null) {
                PostContent(status)
            } else {
                RepostContent(status)
            }

            if (status == statuses.lastOrNull() && !isLoading) {
                LaunchedEffect(status.id) {
                    viewModel.fetchNextPage()
                }
            }
        }
        if (isLoading) {
            item(key = "loading") {
                LoadingIndicator(viewModel, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun LoadingIndicator(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchNextPage()
    }
}
