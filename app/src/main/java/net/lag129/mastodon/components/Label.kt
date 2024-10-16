package net.lag129.mastodon.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusActionBar(repliesCount: Int, reblogsCount: Int, favouritesCount: Int) {
    Row {
        ReplyLabel(repliesCount)
        BoostLabel(reblogsCount)
        FavoriteLabel(favouritesCount)
        BookmarkLabel()
//        ShareButtonLabel()
    }
}

@Composable
private fun ReplyLabel(repliesCount: Int) {
    Label(
        icon = Icons.Default.MailOutline,
        count = repliesCount,
        description = "reply"
    )
}

@Composable
private fun BoostLabel(reblogsCount: Int) {
    Label(
        icon = Icons.Default.Refresh,
        count = reblogsCount,
        description = "boost"
    )
}

@Composable
private fun FavoriteLabel(favouritesCount: Int) {
    Label(
        icon = Icons.Default.Star,
        count = favouritesCount,
        description = "favorite"
    )
}


@Composable
private fun BookmarkLabel() {
    Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = "bookmark",
        tint = Color.Gray
    )
}

//@Composable
//private fun ShareButtonLabel() {
//    Icon(
//        imageVector = Icons.Default.Share,
//        contentDescription = "share",
//        modifier = Modifier.padding(10.dp)
//    )
//}

@Composable
private fun Label(icon: ImageVector, count: Int, description: String? = null) {
    Row(modifier = Modifier.padding(end = 16.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = count.toString(),
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}