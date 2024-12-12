package net.lag129.mastodon.ui.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import net.lag129.mastodon.data.Reaction

@Composable
fun ReactionBar(reactions: List<Reaction>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(36.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        reactions.forEach { reaction ->
            ReactionButton(
                reaction = reaction,
            )
            Spacer(Modifier.width(10.dp))
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
@Composable
fun ReactionButton(reaction: Reaction, modifier: Modifier = Modifier) {
    FilledTonalButton(
        onClick = { },
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