package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import net.lag129.mastodon.data.model.CustomEmoji
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

@Composable
fun HtmlText(
    html: String,
    @SuppressLint("ComposeUnstableCollections") emojis: List<CustomEmoji>,
    modifier: Modifier = Modifier,
    color: Color? = null,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit? = null
) {
    val annotatedString = buildAnnotatedString {
        val doc = Jsoup.parseBodyFragment(html)
        AppendHtmlNodes(doc.body().childNodes(), this)
    }

    val inlineContent = mutableMapOf<String, InlineTextContent>()
    for (emoji in emojis) {
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
        text = appendEmojisToText(annotatedString, emojis),
        inlineContent = inlineContent,
        color = color ?: Color.Unspecified,
        fontSize = fontSize ?: TextUnit.Unspecified,
        fontWeight = fontWeight ?: FontWeight.Normal,
        lineHeight = lineHeight ?: TextUnit.Unspecified,
        modifier = modifier
    )
}

@SuppressLint("ComposeUnstableCollections")
@Composable
private fun appendEmojisToText(
    originalText: AnnotatedString,
    emojis: List<CustomEmoji>
): AnnotatedString {
    val plainText = originalText.text
    val shortcodePattern = Regex(":([a-zA-Z0-9_]+):")

    val emojiMap = remember(emojis) {
        emojis.associateBy { it.shortcode }
    }

    return buildAnnotatedString {
        var lastIndex = 0

        for (matchResult in shortcodePattern.findAll(plainText)) {
            val shortcode = matchResult.groupValues[1]
            val emoji = emojiMap[shortcode]

            append(originalText.subSequence(lastIndex, matchResult.range.first))

            if (emoji != null) {
                appendInlineContent(shortcode, shortcode)
            } else {
                append(
                    originalText.subSequence(
                        matchResult.range.first,
                        matchResult.range.last + 1
                    )
                )
            }

            lastIndex = matchResult.range.last + 1
        }

        if (lastIndex < plainText.length) {
            append(originalText.subSequence(lastIndex, plainText.length))
        }
    }
}

@Composable
private fun AppendHtmlNodes(
    @SuppressLint("ComposeUnstableCollections") nodes: List<Node>,
    builder: AnnotatedString.Builder
) {
    nodes.forEach { node ->
        when (node) {
            is Element -> {
                val style = when (node.tagName()) {
                    "b", "strong" -> SpanStyle(fontWeight = FontWeight.Bold)
                    "i", "em" -> SpanStyle(fontStyle = FontStyle.Italic)
                    "u" -> SpanStyle(textDecoration = TextDecoration.Underline)
                    else -> null
                }
                style?.let { builder.pushStyle(it) }

                when (node.tagName()) {
                    "a" -> {
                        val href = node.attr("href")
                        if (href.isNotBlank()) {
                            builder.pushLink(
                                LinkAnnotation.Url(
                                    href
                                )
                            )
                            builder.pushStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                )
                            )

                            val linkUrl = node.text()
                            val maxUrlLength = 28

                            if (linkUrl.length > maxUrlLength) {
                                val shortUrl = "${linkUrl.take(maxUrlLength)}..."
                                builder.append(shortUrl)
                            } else {
                                AppendHtmlNodes(node.childNodes(), builder)
                            }

                            builder.pop()
                            builder.pop()
                        } else {
                            AppendHtmlNodes(node.childNodes(), builder)
                        }
                    }

                    "br" -> builder.append("\n")

                    "p" -> {
                        if (builder.length > 0) {
                            builder.pushStyle(ParagraphStyle(lineHeight = 16.sp))
                            builder.pop()
                        }
                        AppendHtmlNodes(node.childNodes(), builder)
                    }

                    "ul", "ol" -> {
                        builder.pushStyle(ParagraphStyle(lineHeight = 8.sp))
                        builder.pop()
                        val listItems = node.children().filter { it.tagName() == "li" }
                        listItems.forEachIndexed { index, child ->
                            if (child.tagName() == "li") {
                                builder.append(
                                    if (node.tagName() == "ol") "${index + 1}. " else "• "
                                )
                                AppendHtmlNodes(child.childNodes(), builder)
                                if (index < listItems.size - 1) {
                                    builder.append("\n")
                                }
                            }
                        }
                    }

                    else -> AppendHtmlNodes(node.childNodes(), builder)
                }
                style?.let { builder.pop() }
            }

            is TextNode -> {
                builder.append(node.text())
            }
        }
    }
}