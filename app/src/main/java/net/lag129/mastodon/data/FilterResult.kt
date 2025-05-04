package net.lag129.mastodon.data

import kotlinx.serialization.Serializable

@Serializable
data class FilterResult(
    val filter: Filter,
    val keywordMatches: String? = null,
    val statusMatches: String? = null
)

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

@Serializable
data class FilterKeyword(
    val id: String,
    val keyword: String,
    val wholeWord: Boolean
)

@Serializable
data class FilterStatus(
    val id: String,
    val statusId: String
)