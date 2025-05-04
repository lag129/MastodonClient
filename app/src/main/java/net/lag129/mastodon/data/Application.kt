package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val name: String,
    val website: String? = null
)