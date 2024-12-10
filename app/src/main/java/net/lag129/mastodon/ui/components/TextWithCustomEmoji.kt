package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import net.lag129.mastodon.data.CustomEmoji

@Composable
fun TextWithCustomEmoji(
    text: String,
    @SuppressLint("ComposeUnstableCollections")
    emojis: List<CustomEmoji>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val plainText = parseHtmlToPlainText(text)

    val annotatedString = buildAnnotatedString {
        var currentIndex = 0
        emojis.forEach { emoji ->
            val startIndex = plainText.indexOf(":${emoji.shortcode}:", currentIndex)
            if (startIndex != -1) {
                append(plainText.substring(currentIndex, startIndex))
                appendInlineContent(emoji.shortcode, emoji.shortcode)
                currentIndex = startIndex + emoji.shortcode.length + 2
            }
        }
        append(plainText.substring(currentIndex))
    }

    val inlineContent = emojis.associate { emoji ->
        emoji.shortcode to InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.Center)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(emoji.url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )
        }
    }

    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        modifier = modifier.fillMaxWidth()
    )
}

//private fun parseHtmlToPlainText(html: String): String {
//    val document = Jsoup.parse(html)
////    document.getElementsByTag("p").forEach {
////        it.attr("rel", "nofollow")
////        it.attr("target", "_blank")
////    }
//
//    return document.getElementsByTag("p").html()
//}

private fun parseHtmlToPlainText(html: String): String {
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}