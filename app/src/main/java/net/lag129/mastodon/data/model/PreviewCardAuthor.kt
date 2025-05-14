package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PreviewCardAuthor(
    val name: String,
    val url: String,
    val account: Account?
)