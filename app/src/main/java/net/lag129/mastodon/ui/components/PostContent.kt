package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import net.lag129.mastodon.data.model.Account
import net.lag129.mastodon.data.model.CustomEmoji
import net.lag129.mastodon.data.model.MediaAttachment
import net.lag129.mastodon.data.model.Status

@Composable
fun PostContent(
    status: Status,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            start = 12.dp,
            top = 12.dp,
            end = 16.dp,
            bottom = 12.dp
        )
    ) {
        Row {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(width = 50.dp, height = 55.dp)
            ) {
                AvatarImage(status.account, status.account.url)
            }
            Column {
                val displayName =
                    status.reblog?.account?.displayName ?: status.account.displayName
                DisplayNameBox(displayName, status.account.emojis)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AcctBox(
                        status.account.acct,
                        modifier = Modifier.alignByBaseline()
                    )
                    CreatedAtBox(
                        status.createdAt,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(start = 50.dp)
        ) {
            if (status.content.isNotEmpty()) {
                ContentBox(status.content, status.emojis)
                Spacer(Modifier.height(10.dp))
            }
            if (status.mediaAttachments.isNotEmpty()) {
                DisplayImageView(status.mediaAttachments[0])
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
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp,
        modifier = Modifier
    )
}

@Composable
private fun AcctBox(
    acct: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "@$acct",
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun CreatedAtBox(
    createdAt: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = calculateTimeAgo(createdAt),
        color = Color.Gray,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        modifier = modifier
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
            fontSize = 16.sp,
            modifier = Modifier
        )
    }
}

@Composable
private fun DisplayImageView(mediaAttachments: MediaAttachment) {
    var showFullScreen by remember { mutableStateOf(false) }

    val imageUrl = if (!mediaAttachments.previewUrl.isNullOrEmpty()) {
        mediaAttachments.previewUrl
    } else {
        mediaAttachments.url
    }

    Box(
        modifier = Modifier
            .aspectRatio(ratio = 1.618f)
            .clickable { showFullScreen = true }
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        AsyncImage(
            url = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }

    AnimatedVisibility(
        visible = showFullScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Dialog(
            onDismissRequest = { showFullScreen = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false,
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AsyncImage(
                    url = mediaAttachments.url,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


private fun calculateTimeAgo(createdTimeString: String): String {
    val createdTimeMillis = Instant.parse(createdTimeString).toEpochMilliseconds()
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    return DateUtils.getRelativeTimeSpanString(
        createdTimeMillis,
        currentTimeMillis,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}
