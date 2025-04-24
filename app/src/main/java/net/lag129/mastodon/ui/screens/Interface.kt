package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.lag129.mastodon.DataViewModel
import net.lag129.mastodon.components.TimelineViewLayout

@Composable
fun DataScreen(
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
                TimelineViewLayout(status)
            }
        }
        Button(
            onClick = { viewModel.fetchNextPage() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Load More")
        }
    }
}

@Preview
@Composable
private fun PreviewDataScreen() {
    val viewModel = DataViewModel()
    DataScreen(viewModel)
}
