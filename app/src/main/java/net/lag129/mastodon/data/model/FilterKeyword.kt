package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterKeyword(
    val id: String,
    val keyword: String,
    val wholeWord: Boolean
)