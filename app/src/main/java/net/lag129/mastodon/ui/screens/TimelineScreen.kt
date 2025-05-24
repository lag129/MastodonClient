package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.lag129.mastodon.ui.components.PostContent
import net.lag129.mastodon.ui.components.RepostContent
import net.lag129.mastodon.viewmodel.DataViewModel

@Composable
fun TimelineScreen(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    val data by remember { viewModel.data }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(data, key = { _, item -> item.id }) { index, status ->
            if (status.reblog == null) {
                PostContent(status)
            } else {
                RepostContent(status)
            }

            if (index >= data.size - 5 && !isLoading) {
                LaunchedEffect(index) {
                    viewModel.fetchNextPage()
                }
            }
        }
        if (isLoading) {
            item {
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
