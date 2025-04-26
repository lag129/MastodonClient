package net.lag129.mastodon.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.lag129.mastodon.ApiClient
import net.lag129.mastodon.data.Reaction

@Composable
fun ReactionBar(
    statusId: String,
    @SuppressLint("ComposeUnstableCollections") reactions: List<Reaction>,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    var reactionList by remember { mutableStateOf(reactions) }

    Row(
        modifier = modifier
            .height(36.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        reactionList.forEach { reaction ->
            ReactionButton(
                reaction = reaction,
                modifier = Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    coroutineScope.launch {
                        try {
                            if (reaction.me) {
                                ApiClient.apiService.deleteReaction(
                                    statusId,
                                    reaction.name
                                )
                                reactionList = reactionList.map {
                                    if (it == reaction) it.copy(
                                        count = it.count - 1,
                                        me = false
                                    ) else it
                                }
                            } else {
                                ApiClient.apiService.postReaction(
                                    statusId,
                                    reaction.name
                                )
                                reactionList = reactionList.map {
                                    if (it == reaction) it.copy(
                                        count = it.count + 1,
                                        me = true
                                    ) else it
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Composable
fun ReactionButton(reaction: Reaction, modifier: Modifier = Modifier) {
    FilledTonalButton(
        onClick = {},
        enabled = reaction.me,
    ) {
        Row {
            reaction.url?.let {
                if (it.isEmpty()) {
                    Text(reaction.name)
                } else {
                    AsyncImage(
                        url = it,
                        modifier = modifier
                    )
                }
            }
            Text(reaction.count.toString())
        }
    }
}