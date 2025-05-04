package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String,
    val url: String
)