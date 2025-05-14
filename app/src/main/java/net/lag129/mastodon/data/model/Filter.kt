package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val id: String,
    val title: String,
    val context: List<String>,
    val expiresAt: String? = null,
    val filterAction: String,
    val keywords: List<FilterKeyword>,
    val statuses: List<FilterStatus>
)