package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String,
    val url: String
)