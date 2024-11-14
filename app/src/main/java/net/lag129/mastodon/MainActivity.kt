package net.lag129.mastodon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import net.lag129.mastodon.components.MyNavigationBar
import net.lag129.mastodon.ui.theme.MastodonTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MastodonTheme {
                Scaffold(
                    bottomBar = { MyNavigationBar() }
                ) {
                    DataScreen(DataViewModel())
                }
            }
        }
    }
}
