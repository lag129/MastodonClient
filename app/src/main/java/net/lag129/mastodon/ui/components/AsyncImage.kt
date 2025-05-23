package net.lag129.mastodon.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun AsyncImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale? = null
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(AnimatedImageDecoder.Factory())
        }
        .build()
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale ?: ContentScale.Fit,
        imageLoader = imageLoader,
        modifier = modifier
    )
}