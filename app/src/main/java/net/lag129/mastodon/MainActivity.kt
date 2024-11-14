package net.lag129.mastodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import net.lag129.mastodon.ui.components.MyNavigationBar
import net.lag129.mastodon.ui.screens.DataScreen
import net.lag129.mastodon.ui.theme.MastodonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MastodonTheme {
                Scaffold(
                    bottomBar = { MyNavigationBar() }
                ) { paddingValues ->
                    DataScreen(
                        viewModel = DataViewModel(),
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}
