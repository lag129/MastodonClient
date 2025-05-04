package net.lag129.mastodon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val id: String,
    val uri: String,
    @SerialName("created_at") val createdAt: String,
    val account: Account,
    val content: String,
    val visibility: String,
    val sensitive: Boolean,
    @SerialName("spoiler_text") val spoilerText: String,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment>,
    val application: Application? = null,
    val mentions: List<Mention>,
    val tags: List<Tag>,
    val emojis: List<CustomEmoji>,
    @SerialName("reblogs_count") val reblogsCount: Int,
    @SerialName("favourites_count") val favouritesCount: Int,
    @SerialName("replies_count") val repliesCount: Int,
    val url: String? = null,
    @SerialName("in_reply_to_id") val inReplyToId: String? = null,
    @SerialName("in_reply_to_account_id") val inReplyToAccountId: String? = null,
    val reblog: Status? = null,
    val poll: Poll? = null,
    val card: PreviewCard? = null,
    val language: String? = null,
    val text: String? = null,
    @SerialName("edited_at") val editedAt: String? = null,
    val favourited: Boolean? = null,
    val reblogged: Boolean? = null,
    val muted: Boolean?,
    val bookmarked: Boolean?,
    val pinned: Boolean? = null,
    val filtered: List<FilterResult>?,
    @SerialName("emoji_reactions") val emojiReactions: List<Reaction>?,
)

@Serializable
data class Mention(
    val id: String,
    val username: String,
    val url: String,
    val acct: String
)