package net.lag129.mastodon

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DataScreen(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    val data by remember { viewModel.data }
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(data) { _, status ->
            TimelineViewLayout(status)
        }
        item {
            Button(onClick = { viewModel.fetchNextPage() }) {
                Text("Load More")
            }
            // ボタンをナビゲーションバーの上に表示させるための暫定的な対応
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewDataScreen() {
    val viewModel = DataViewModel()
    DataScreen(viewModel)
}
