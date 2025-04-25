package net.lag129.mastodon.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        val doc = Jsoup.parseBodyFragment(html)
        AppendHtmlNodes(doc.body().childNodes(), this)
    }

    Text(
        text = annotatedString,
        modifier = modifier
    )
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
                        }
                        AppendHtmlNodes(node.childNodes(), builder)
                        if (href.isNotBlank()) {
                            builder.pop()
                            builder.pop()
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