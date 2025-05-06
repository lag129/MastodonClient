package net.lag129.mastodon

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.lag129.mastodon.components.BottomNavBar
import net.lag129.mastodon.ui.screens.FirstLaunchScreen
import net.lag129.mastodon.ui.screens.TimelineScreen
import net.lag129.mastodon.ui.theme.MastodonTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authViewModel: AuthViewModel by viewModels()
        val viewModel: DataViewModel by viewModels()

        setContent {
            val navController = rememberNavController()
            val serverName by authViewModel.serverName.collectAsStateWithLifecycle()
            val bearerToken by authViewModel.bearerToken.collectAsStateWithLifecycle()
            val coroutineScope = rememberCoroutineScope()

            val isLoggedIn = !serverName.isNullOrEmpty() && !bearerToken.isNullOrEmpty()

            MastodonTheme {
                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        Scaffold { paddingValues ->
                            FirstLaunchScreen(
                                authViewModel = authViewModel,
                                onServerNameChanged = { serverName ->
                                    coroutineScope.launch {
                                        authViewModel.setServerName(serverName)
                                        println("サーバー名: $serverName")
                                    }
                                },
                                onBearerTokenChanged = { token ->
                                    coroutineScope.launch {
                                        authViewModel.setBearerToken(token)
                                        println("トークン: $token")
                                        if (serverName.isNullOrEmpty()) {
                                            navController.navigate(Screen.Home.route) {
                                                popUpTo(Screen.Login.route) { inclusive = true }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }

                    composable(Screen.Home.route) {
                        Scaffold(
                            bottomBar = {
                                BottomNavBar(navController, viewModel)
                            }
                        ) { paddingValues ->
                            TimelineScreen(viewModel, Modifier.padding(paddingValues))
                        }
                    }

                    composable(Screen.Global.route) {
                        Scaffold(
                            bottomBar = {
                                BottomNavBar(navController, viewModel)
                            }
                        ) { paddingValues ->
                            TimelineScreen(viewModel, Modifier.padding(paddingValues))
                        }
                    }

                    composable(Screen.Info.route) {
                        Scaffold(
                            bottomBar = {
                                BottomNavBar(navController, viewModel)
                            }
                        ) { _ ->
                            // TODO: implement
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Login : Screen("Login")
    data object Home : Screen("Home")
    data object Global : Screen("Global")
    data object Info : Screen("Info")
}