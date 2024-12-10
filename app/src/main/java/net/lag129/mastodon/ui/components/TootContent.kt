package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import de.charlex.compose.material3.HtmlText
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import net.lag129.mastodon.data.Account
import net.lag129.mastodon.data.CustomEmoji
import net.lag129.mastodon.data.Status

@Composable
fun TootContent(
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
            DisplayNameBox(displayName)
            Row {
                AcctBox(status.account.acct)
                CreatedAtBox(status.createdAt)
            }
            Spacer(Modifier.height(10.dp))
            if (status.spoilerText.isNotEmpty()) {
//                SpoilerText(status.spoilerText, status.content)
            } else {
                ContentBox(status.content, status.emojis)
            }
            Spacer(Modifier.height(10.dp))
            if (status.mediaAttachments.isNotEmpty()) {
                DisplayImageView(status.mediaAttachments[0].url, status.sensitive)
                Spacer(Modifier.width(10.dp))
            }
            if (status.card != null) {
//                LinkCard(card)
                Spacer(Modifier.height(10.dp))
            }
            ReactionBar(status.emojiReactions ?: emptyList())
            StatusActionBar(
                status.repliesCount,
                status.reblogsCount,
                status.favouritesCount
            )
        }
    }
    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
    println(status.account.acct)
    println(status.emojis)
}

@Composable
private fun AvatarImage(
    account: Account,
    accountUrl: String
) {
    val avatarImageUrl = account.avatar
    var isClicked by remember { mutableStateOf(false) }
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(avatarImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
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
        val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(accountUrl)) }
        context.startActivity(intent)
    }
}

@Composable
private fun DisplayNameBox(displayName: String) {
    Text(
        text = displayName,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
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
    HtmlText(
        text = "@$acct",
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

//@Composable
//private fun SpoilerText(
//    spoilerTxt: String,
//    contentTxt: String
//) {
//    var isClicked by remember { mutableStateOf(false) }
//    val textCount = contentTxt.length
//    Column {
//        Text(
//            text = spoilerTxt
//        )
//        Text(
//            text = if (isClicked) "隠す" else "もっと見る(${textCount}文字)",
//            textDecoration = TextDecoration.Underline,
//            color = Color(0xff00d9c5),
//            modifier = Modifier.clickable { isClicked = !isClicked }
//        )
//    }
//    if (isClicked) {
//        ContentBox(contentTxt)
//    }
//}

@Composable
private fun ContentBox(contentTxt: String, @SuppressLint("ComposeUnstableCollections") emojis: List<CustomEmoji>) {
    SelectionContainer {
        TextWithCustomEmoji(
            text = contentTxt,
            emojis = emojis
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
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(contentImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(ratio = 1.618f)
                .clip(RoundedCornerShape(3))
                .blur(radius = if (isBlurred && isSensitive) 40.dp else 0.dp)
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
        FullScreenImageDialog(contentImageUrl) { isClicked = false }
    }
}

@Composable
private fun FullScreenImageDialog(
    contentImageUrl: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(contentImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun calculateTimeAgo(createdTimeString: String): CharSequence? {
    val createdTimeMillis = Instant.parse(createdTimeString).toEpochMilliseconds()
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    return DateUtils.getRelativeTimeSpanString(createdTimeMillis, currentTimeMillis, DateUtils.MINUTE_IN_MILLIS)
}
