package net.lag129.mastodon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewCard(
    val url: String,
    val title: String,
    val description: String,
    val type: String,
    val authors: List<PreviewCardAuthor>? = null,
    @SerialName("author_name") val authorName: String,
    @SerialName("author_url") val authorUrl: String,
    @SerialName("provider_name") val providerName: String,
    @SerialName("provider_url") val providerUrl: String,
    val html: String,
    val width: Int,
    val height: Int,
    val image: String? = null,
    @SerialName("embed_url") val embedUrl: String,
    val blurhash: String? = null
)