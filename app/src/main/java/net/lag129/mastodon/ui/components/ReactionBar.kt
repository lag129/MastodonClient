package net.lag129.mastodon.ui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import net.lag129.mastodon.data.Reaction

@Composable
fun ReactionBar(reactions: List<Reaction>, modifier: Modifier = Modifier) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = modifier
            .height(36.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        reactions.forEach { reaction ->
            ReactionButton(
                reaction = reaction,
                modifier = Modifier.clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Composable
fun ReactionButton(reaction: Reaction, modifier: Modifier = Modifier) {
    val haptic = LocalHapticFeedback.current
    FilledTonalButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        enabled = reaction.me,
    ) {
        Row {
            reaction.url?.let {
                if (it.isEmpty()) {
                    Text(reaction.name)
                }
                else {
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