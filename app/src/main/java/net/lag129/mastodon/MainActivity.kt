package net.lag129.mastodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.lag129.mastodon.ui.components.MyNavigationBar
import net.lag129.mastodon.ui.screens.DataScreen
import net.lag129.mastodon.ui.theme.MastodonTheme

class MainActivity : ComponentActivity() {
    private val viewModel = DataViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MastodonTheme {
                Scaffold(
                    bottomBar = {
                        MyNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Screen.Home.route) {
                            DataScreen(viewModel = viewModel)
                        }
                        composable(Screen.Global.route) {
                            // TODO: implement
                        }
                        composable(Screen.Info.route) {
                            // TODO: implement
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Global : Screen("global")
    object Info : Screen("info")
}
