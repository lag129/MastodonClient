package net.lag129.mastodon.data


data class Status(
    val id: String,
    val uri: String,
    val createdAt: String,
    val account: Account,
    val content: String,
    val visibility: String,
    val sensitive: Boolean,
    val spoilerText: String,
    val mediaAttachments: List<MediaAttachment>,
    val application: Application?,
    val mentions: List<Mention>,
    val tags: List<Tag>,
    val emojis: List<CustomEmoji>,
    val reblogsCount: Int,
    val favouritesCount: Int,
    val repliesCount: Int,
    val url: String? = null,
    val inReplyToId: String? = null,
    val inReplyToAccountId: String? = null,
    val reblog: Status? = null,
    val poll: Poll? = null,
    val card: PreviewCard? = null,
    val language: String? = null,
    val text: String? = null,
    val editedAt: String? = null,
    val favourited: Boolean? = null,
    val reblogged: Boolean? = null,
    val muted: Boolean?,
    val bookmarked: Boolean?,
    val pinned: Boolean?,
    val filtered: List<FilterResult>?,
    val emojiReactions: List<Reaction>?,
)

data class Account(
    val id: String,
    val username: String,
    val acct: String,
    val url: String,
    val displayName: String,
    val note: String,
    val avatar: String,
    val avatarStatic: String,
    val header: String,
    val headerStatic: String,
    val locked: Boolean,
    val fields: List<Field>,
    val emojis: List<CustomEmoji>,
    val bot: Boolean,
    val group: Boolean,
    val discoverable: Boolean? = null,
    val noindex: Boolean? = null,
    val moved: Account? = null,
    val suspended: Boolean?,
    val limited: Boolean?,
    val createdAt: String,
    val lastStatusAt: String? = null,
    val statusesCount: Int,
    val followersCount: Int,
    val followingCount: Int,
)

data class Field(
    val name: String,
    val value: String,
    val verifiedAt: String? = null
)

data class MediaAttachment(
    val id: String,
    val type: String,
    val url: String,
    val previewUrl: String? = null,
    val remoteUrl: String? = null,
    val meta: Meta,
    val description: String? = null,
    val blurhash: String? = null
)

data class Meta(
    val original: MetaOriginal,
    val small: MetaSmall
)

data class MetaOriginal(
    val width: Int,
    val height: Int,
    val size: String,
    val aspect: Float
)

data class MetaSmall(
    val width: Int,
    val height: Int,
    val size: String,
    val aspect: Float
)

data class Application(
    val name: String,
    val website: String? = null
)

data class Mention(
    val id: String,
    val username: String,
    val url: String,
    val acct: String
)

data class Tag(
    val name: String,
    val url: String
)

data class CustomEmoji(
    val shortcode: String,
    val url: String,
    val staticUrl: String,
    val visibleInPicker: Boolean,
    val category: String? = null
)

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

data class PollOption(
    val title: String,
    val votesCount: Int? = null
)

data class PreviewCard(
    val url: String,
    val title: String,
    val description: String,
    val type: String,
    val authors: List<PreviewCardAuthor>,
    val authorName: String,
    val authorUrl: String,
    val providerName: String,
    val providerUrl: String,
    val html: String,
    val width: Int,
    val height: Int,
    val image: String? = null,
    val embedUrl: String,
    val blurhash: String? = null
)

data class PreviewCardAuthor(
    val name: String,
    val url: String,
    val account: Account?
)

data class Reaction(
    val name: String,
    val count: Int,
    val me: Boolean,
    val url: String?,
    val staticUrl: String?,
    val domain: String?,
    val width: Int?,
    val height: Int?,
    val accountIds: List<String>? = null
)