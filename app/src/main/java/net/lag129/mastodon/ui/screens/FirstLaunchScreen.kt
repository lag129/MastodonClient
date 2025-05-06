package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun FirstLaunchScreen(
    onServerNameChanged: (String) -> Unit,
    onBearerTokenChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var serverName by remember { mutableStateOf("") }
    var bearerToken by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = serverName,
            onValueChange = {
                serverName = it
//                onServerNameChanged(it)
            },
            label = { Text("サーバー名") },
        )
        OutlinedTextField(
            value = bearerToken,
            onValueChange = {
                bearerToken = it
//                onBearerTokenChanged(it)
            },
            label = { Text("トークン") },
        )
        Button(
            onClick = {
                onServerNameChanged(serverName)
                onBearerTokenChanged(bearerToken)
                println("サーバー名: $serverName")
                println("トークン: $bearerToken")
            },
            content = { Text("保存") }
        )
    }
}