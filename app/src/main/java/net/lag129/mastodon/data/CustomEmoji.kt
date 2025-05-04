package net.lag129.mastodon.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomEmoji(
    val shortcode: String,
    val url: String,
    @SerialName("static_url") val staticUrl: String,
    @SerialName("visible_in_picker") val visibleInPicker: Boolean,
    val category: String? = null
)