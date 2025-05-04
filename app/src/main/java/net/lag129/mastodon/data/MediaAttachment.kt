package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class MediaAttachment(
    val id: String,
    val type: String,
    val url: String,
    val previewUrl: String? = null,
    val remoteUrl: String? = null,
    val meta: Meta,
    val description: String? = null,
    val blurhash: String? = null
)

@Serializable
data class Meta(
    val original: MetaOriginal,
    val small: MetaSmall
)

@Serializable
data class MetaOriginal(
    val width: Int,
    val height: Int,
    val size: String,
    val aspect: Float
)

@Serializable
data class MetaSmall(
    val width: Int,
    val height: Int,
    val size: String,
    val aspect: Float
)