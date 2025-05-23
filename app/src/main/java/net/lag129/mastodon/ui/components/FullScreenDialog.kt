package net.lag129.mastodon.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenDialog(
    contentImageUrl: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onClose,
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .clickable { onClose() }
        ) {
            AsyncImage(
                contentImageUrl,
                modifier = Modifier
            )
        }
    }
}