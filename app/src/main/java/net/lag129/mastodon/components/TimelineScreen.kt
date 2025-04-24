package net.lag129.mastodon.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.lag129.mastodon.data.Status

@Composable
fun TimelineViewLayout(status: Status) {
    if (status.reblog == null) {
        TootContent(status)
    } else {
        BoostNameBox(status.account.displayName)
        TootContent(status.reblog)
    }
}

@Composable
private fun BoostNameBox(boostName: String) {
    Text(
        text = "\uD83D\uDD01  $boostName さんがブースト",
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(
            start = 35.dp,
            top = 10.dp,
            end = 15.dp
        )
    )
}
