package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class FilterStatus(
    val id: String,
    val statusId: String
)