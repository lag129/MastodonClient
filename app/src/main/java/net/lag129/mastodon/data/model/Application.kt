package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val name: String,
    val website: String? = null
)