package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterResult(
    val filter: Filter,
    val keywordMatches: String? = null,
    val statusMatches: String? = null
)