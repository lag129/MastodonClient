package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.lag129.mastodon.data.model.CustomEmoji
import net.lag129.mastodon.data.model.Status

@Composable
fun RepostContent(status: Status) {
    BoostNameBox(
        status.account.displayName,
        status.account.emojis
    )
    PostContent(status.reblog!!)
}

@Composable
private fun BoostNameBox(
    boostName: String,
    @SuppressLint("ComposeUnstableCollections") emojis: List<CustomEmoji>
) {
    HtmlText(
        html = "\uD83D\uDD01  $boostName さんがブースト",
        emojis = emojis,
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.padding(
            start = 35.dp,
            top = 10.dp,
            end = 15.dp,
        )
    )
}
