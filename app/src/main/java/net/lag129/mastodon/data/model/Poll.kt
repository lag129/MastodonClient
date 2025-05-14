package net.lag129.mastodon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Poll(
    val id: String,
    val expiresAt: String? = null,
    val expired: Boolean,
    val multiple: Boolean,
    val votesCount: Int,
    val votersCount: Int? = null,
    val options: List<PollOption>,
    val emojis: List<CustomEmoji>,
    val voted: Boolean?,
    val ownVotes: List<Int>
)

@Serializable
data class PollOption(
    val title: String,
    val votesCount: Int? = null
)