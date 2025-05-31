package net.lag129.mastodon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaAttachment(
    val id: String,
    val type: String,
    val url: String,
    @SerialName("preview_url") val previewUrl: String? = null,
    @SerialName("remote_url") val remoteUrl: String? = null,
    val meta: Meta? = null,
    val description: String? = null,
    val blurhash: String? = null
)

@Serializable
data class Meta(
    val original: MetaOriginal? = null,
    val small: MetaSmall? = null
)

@Serializable
data class MetaOriginal(
    val width: Int,
    val height: Int,
    val size: String? = null,
    val aspect: Float? = null
)

@Serializable
data class MetaSmall(
    val width: Int,
    val height: Int,
    val size: String,
    val aspect: Float
)