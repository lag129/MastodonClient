package net.lag129.mastodon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    val username: String,
    val acct: String,
    val url: String,
    @SerialName("display_name") val displayName: String,
    val note: String,
    val avatar: String,
    @SerialName("avatar_static") val avatarStatic: String,
    val header: String,
    @SerialName("header_static") val headerStatic: String,
    val locked: Boolean,
    val fields: List<Field>,
    val emojis: List<CustomEmoji>,
    val bot: Boolean,
    val group: Boolean,
    val discoverable: Boolean? = null,
    val noindex: Boolean? = null,
    val moved: Account? = null,
    val suspended: Boolean? = null,
    val limited: Boolean? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("last_status_at") val lastStatusAt: String? = null,
    @SerialName("statuses_count") val statusesCount: Int,
    @SerialName("followers_count") val followersCount: Int,
    @SerialName("following_count") val followingCount: Int,
)

@Serializable
data class Field(
    val name: String,
    val value: String,
    val verifiedAt: String? = null
)