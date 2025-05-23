package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import net.lag129.mastodon.data.model.Account
import net.lag129.mastodon.data.model.CustomEmoji
import net.lag129.mastodon.data.model.Status

@Composable
fun PostContent(
    status: Status,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            start = 12.dp,
            top = 12.dp,
            end = 16.dp,
            bottom = 12.dp
        )
    ) {
        AvatarImage(status.account, status.account.url)
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            val displayName = status.reblog?.account?.displayName ?: status.account.displayName
            DisplayNameBox(displayName, status.account.emojis)
            Row {
                AcctBox(status.account.acct)
                CreatedAtBox(status.createdAt)
            }
            ContentBox(status.content, status.emojis)
            Spacer(Modifier.height(10.dp))
            if (status.mediaAttachments.isNotEmpty()) {
                DisplayImageView(status.mediaAttachments[0].url, status.sensitive)
                Spacer(Modifier.height(10.dp))
            }
            if (!status.emojiReactions.isNullOrEmpty()) {
                ReactionBar(status.id, status.emojiReactions)
                Spacer(Modifier.height(10.dp))
            }
            StatusActionBar(
                status.repliesCount,
                status.reblogsCount,
                status.favouritesCount
            )
        }
    }
    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}

@Composable
private fun AvatarImage(
    account: Account,
    accountUrl: String
) {
    val avatarImageUrl = account.avatar
    var isClicked by remember { mutableStateOf(false) }

    AsyncImage(
        url = avatarImageUrl,
        modifier = Modifier
            .padding(top = 5.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(30))
            .clickable {
                isClicked = true
            }
    )
    if (isClicked) {
        val context = LocalContext.current
        val intent = remember { Intent(Intent.ACTION_VIEW, accountUrl.toUri()) }
        context.startActivity(intent)
    }
}

@Composable
private fun DisplayNameBox(
    displayName: String,
    @SuppressLint("ComposeUnstableCollections") emojis: List<CustomEmoji>
) {
    HtmlText(
        html = displayName,
        emojis = emojis,
        modifier = Modifier
    )
}

@Composable
private fun CreatedAtBox(createdAt: String) {
    Text(
        text = calculateTimeAgo(createdAt).toString(),
        color = Color.Gray,
        fontSize = 12.sp,
        textAlign = TextAlign.End
    )
}

@Composable
private fun AcctBox(acct: String) {
    Text(
        text = "@$acct",
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ContentBox(
    contentTxt: String,
    @SuppressLint("ComposeUnstableCollections") emojis: List<CustomEmoji>
) {
    SelectionContainer(
        modifier = Modifier.selectableGroup()
    ) {
        HtmlText(
            html = contentTxt,
            emojis = emojis,
            modifier = Modifier
        )
    }
}

@Composable
private fun DisplayImageView(
    contentImageUrl: String,
    isSensitive: Boolean = false,
    isBlurredInitially: Boolean = true
) {
    var isClicked by remember { mutableStateOf(false) }
    var isBlurred by remember { mutableStateOf(isBlurredInitially) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(3))
            .fillMaxSize()
            .clickable { isClicked = true }
    ) {
        AsyncImage(
            url = contentImageUrl,
            modifier = Modifier
                .aspectRatio(ratio = 1.618f)
                .clip(RoundedCornerShape(3))
                .blur(radius = if (isBlurred && isSensitive) 40.dp else 0.dp),
            contentScale = ContentScale.Crop
        )
        if (isBlurred && isSensitive) {
            Text(
                text = "閲覧注意",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
    if (isClicked) {
        FullScreenDialog(
            contentImageUrl,
            { isClicked = false }
        )
    }
}

private fun calculateTimeAgo(createdTimeString: String): CharSequence? {
    val createdTimeMillis = Instant.parse(createdTimeString).toEpochMilliseconds()
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    return DateUtils.getRelativeTimeSpanString(
        createdTimeMillis,
        currentTimeMillis,
        DateUtils.MINUTE_IN_MILLIS
    )
}
