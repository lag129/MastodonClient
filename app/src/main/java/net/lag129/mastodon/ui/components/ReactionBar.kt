package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import coil3.request.crossfade
import net.lag129.mastodon.data.Reaction

@Composable
fun ReactionBar(reactions: List<Reaction>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(36.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        reactions.forEach { reaction ->
            ReactionButton(
                reaction = reaction,
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
@Composable
fun ReactionButton(reaction: Reaction, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    FilledTonalButton(
        onClick = { },
        enabled = reaction.me,
    ) {
        Row {
            reaction.url?.let {
                if (it.isEmpty()) {
                    Text(reaction.name)
                }
                else {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(reaction.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        imageLoader = imageLoader,
                        modifier = Modifier.height(32.dp)
                    )
                }
            }
            Text(reaction.count.toString())
        }
    }
}