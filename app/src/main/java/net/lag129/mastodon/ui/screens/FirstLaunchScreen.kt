package net.lag129.mastodon.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.lag129.mastodon.AuthViewModel

@Composable
fun FirstLaunchScreen(
    authViewModel: AuthViewModel,
    onServerNameChanged: (String) -> Unit,
    onBearerTokenChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val serverName by authViewModel.serverName.collectAsStateWithLifecycle()
    val bearerToken by authViewModel.bearerToken.collectAsStateWithLifecycle()

    var inputServerName by remember { mutableStateOf(serverName ?: "") }
    var inputBearerToken by remember { mutableStateOf(bearerToken ?: "") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = inputServerName,
            onValueChange = {
                inputServerName = it
            },
            label = { Text("サーバー名") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        OutlinedTextField(
            value = inputBearerToken,
            onValueChange = {
                inputBearerToken = it
            },
            label = { Text("トークン") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onServerNameChanged(inputServerName)
                onBearerTokenChanged(inputBearerToken)
                println("サーバー名: $inputServerName")
                println("トークン: $inputBearerToken")
            },
            content = { Text("保存") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}