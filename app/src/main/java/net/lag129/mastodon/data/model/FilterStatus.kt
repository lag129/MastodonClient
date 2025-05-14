package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterStatus(
    val id: String,
    val statusId: String
)