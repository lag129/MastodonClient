package net.lag129.mastodon.data.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import net.lag129.mastodon.data.model.Account
import net.lag129.mastodon.data.model.Application
import net.lag129.mastodon.data.model.CustomEmoji
import net.lag129.mastodon.data.model.FilterResult
import net.lag129.mastodon.data.model.MediaAttachment
import net.lag129.mastodon.data.model.Mention
import net.lag129.mastodon.data.model.Poll
import net.lag129.mastodon.data.model.PreviewCard
import net.lag129.mastodon.data.model.Reaction
import net.lag129.mastodon.data.model.Status
import net.lag129.mastodon.data.model.Tag

@Suppress("unused")
class StatusConverters {
    private val json = Json { ignoreUnknownKeys = true }

    // Account変換
    @TypeConverter
    fun fromAccount(account: Account): String {
        return json.encodeToString(account)
    }

    @TypeConverter
    fun toAccount(value: String): Account {
        return json.decodeFromString(value)
    }

    // List<MediaAttachment>変換
    @TypeConverter
    fun fromMediaAttachments(attachments: List<MediaAttachment>): String {
        return json.encodeToString(attachments)
    }

    @TypeConverter
    fun toMediaAttachments(value: String): List<MediaAttachment> {
        return if (value.isBlank()) emptyList() else json.decodeFromString(value)
    }

    // Application変換
    @TypeConverter
    fun fromApplication(application: Application?): String {
        return application?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toApplication(value: String): Application? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }

    // List<Mention>変換
    @TypeConverter
    fun fromMentions(mentions: List<Mention>): String {
        return json.encodeToString(mentions)
    }

    @TypeConverter
    fun toMentions(value: String): List<Mention> {
        return if (value.isBlank()) emptyList() else json.decodeFromString(value)
    }

    // List<Tag>変換
    @TypeConverter
    fun fromTags(tags: List<Tag>): String {
        return json.encodeToString(tags)
    }

    @TypeConverter
    fun toTags(value: String): List<Tag> {
        return if (value.isBlank()) emptyList() else json.decodeFromString(value)
    }

    // List<CustomEmoji>変換
    @TypeConverter
    fun fromEmojis(emojis: List<CustomEmoji>): String {
        return json.encodeToString(emojis)
    }

    @TypeConverter
    fun toEmojis(value: String): List<CustomEmoji> {
        return if (value.isBlank()) emptyList() else json.decodeFromString(value)
    }

    // Status変換
    @TypeConverter
    fun fromStatus(status: Status?): String {
        return status?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toStatus(value: String): Status? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }

    // Poll変換
    @TypeConverter
    fun fromPoll(poll: Poll?): String {
        return poll?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toPoll(value: String): Poll? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }

    // PreviewCard変換
    @TypeConverter
    fun fromPreviewCard(card: PreviewCard?): String {
        return card?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toPreviewCard(value: String): PreviewCard? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }

    // List<FilterResult>変換
    @TypeConverter
    fun fromFilterResults(filters: List<FilterResult>?): String {
        return filters?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toFilterResults(value: String): List<FilterResult>? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }

    // List<Reaction>変換
    @TypeConverter
    fun fromReactions(reactions: List<Reaction>?): String {
        return reactions?.let { json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toReactions(value: String): List<Reaction>? {
        return if (value.isBlank()) null else json.decodeFromString(value)
    }
}