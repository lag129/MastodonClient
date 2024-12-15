package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import net.lag129.mastodon.data.CustomEmoji
import org.jsoup.Jsoup
import org.jsoup.safety.Safelist

@Composable
fun TextWithCustomEmoji(
    text: String,
    @SuppressLint("ComposeUnstableCollections")
    emojis: List<CustomEmoji>,
    modifier: Modifier = Modifier
) {
    val plainText = parseHtmlToPlainText(text)

    val annotatedString = buildAnnotatedString {
        var currentIndex = 0
        while (currentIndex < plainText.length) {
            val nextEmoji = emojis.minByOrNull { emoji ->
                val index = plainText.indexOf(":${emoji.shortcode}:", currentIndex)
                if (index == -1) Int.MAX_VALUE else index
            }
            if (nextEmoji != null) {
                val startIndex = plainText.indexOf(":${nextEmoji.shortcode}:", currentIndex)
                if (startIndex != -1) {
                    append(plainText.substring(currentIndex, startIndex))
                    appendInlineContent(nextEmoji.shortcode, nextEmoji.shortcode)
                    currentIndex = startIndex + nextEmoji.shortcode.length + 2
                } else {
                    append(plainText.substring(currentIndex))
                    break
                }
            } else {
                append(plainText.substring(currentIndex))
                break
            }
        }
    }

    val inlineContent = mutableMapOf<String, InlineTextContent>()
    emojis.forEach { emoji ->
        inlineContent[emoji.shortcode] = InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.Center)
        ) {
            AsyncImage(
                url = emoji.url,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        modifier = modifier.fillMaxWidth()
    )
}

private fun parseHtmlToPlainText(html: String): String {
    return Jsoup.clean(html, Safelist.none())
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .trim()
}