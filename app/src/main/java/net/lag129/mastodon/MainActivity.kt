package net.lag129.mastodon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.lag129.mastodon.components.BottomNavBar
import net.lag129.mastodon.ui.screens.TimelineScreen
import net.lag129.mastodon.ui.theme.MastodonTheme

private val Context.dataStore by preferencesDataStore("settings")
private val isFirstLaunch = booleanPreferencesKey("isFirstLaunch")
private val serverName = stringPreferencesKey("serverName")

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
                        BottomNavBar(navController = navController, viewModel = viewModel)
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Screen.Home.route) {
                            TimelineScreen(viewModel = viewModel)
                        }
                        composable(Screen.Global.route) {
                            TimelineScreen(viewModel = viewModel)
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
    data object Home : Screen("Home")
    data object Global : Screen("Global")
    data object Info : Screen("Info")
}
