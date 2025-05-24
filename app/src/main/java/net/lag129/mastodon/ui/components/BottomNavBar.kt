package net.lag129.mastodon.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import net.lag129.mastodon.Screen
import net.lag129.mastodon.viewmodel.DataViewModel

@Composable
fun BottomNavBar(
    navController: NavController,
    viewModel: DataViewModel,
    modifier: Modifier = Modifier
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomAppBar(
        modifier = modifier.height(90.dp)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text(text = "Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.HOME)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Global") },
            label = { Text(text = "Global") },
            selected = currentRoute == Screen.Global.route,
            onClick = {
                navController.navigate(Screen.Global.route) {
                    popUpTo(Screen.Global.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.GLOBAL)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = "User") },
            label = { Text(text = "User") },
            selected = currentRoute == Screen.User.route,
            onClick = {
                navController.navigate(Screen.User.route) {
                    popUpTo(Screen.User.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.USER)
            }
        )
    }
}
