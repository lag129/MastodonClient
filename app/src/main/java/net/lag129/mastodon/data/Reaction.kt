package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val name: String,
    val count: Int,
    val me: Boolean,
    val url: String? = null,
    val staticUrl: String? = null,
    val domain: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val accountIds: List<String>? = null
)