package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.lag129.mastodon.ui.components.RepostContent
import net.lag129.mastodon.viewmodel.DataViewModel

@Composable
fun TimelineScreen(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    val data by remember { viewModel.data }
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(data, key = { index, item -> item.id }) { _, status ->
                RepostContent(status)
            }
            item { LoadingIndicator(viewModel) }
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
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchNextPage()
    }
}
