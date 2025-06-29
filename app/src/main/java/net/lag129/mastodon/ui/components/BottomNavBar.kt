package net.lag129.mastodon.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import net.lag129.mastodon.R
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
            icon = {
                Icon(
                    painterResource(R.drawable.house_duotone),
                    contentDescription = "Home",
                    modifier = Modifier.height(24.dp)
                )
            },
//            label = { Text(text = "ホーム") },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.HOME)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.bell_duotone),
                    contentDescription = "Notification",
                    modifier = Modifier.height(24.dp)
                )
            },
//            label = { Text(text = "通知") },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.fediverse_logo_duotone),
                    contentDescription = "Global",
                    modifier = Modifier.height(24.dp)
                )
            },
//            label = { Text(text = "連合") },
            selected = currentRoute == Screen.Global.route,
            onClick = {
                navController.navigate(Screen.Global.route) {
                    popUpTo(Screen.Global.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.GLOBAL)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.user_circle_duotone),
                    contentDescription = "User",
                    modifier = Modifier.height(24.dp)
                )
            },
//            label = { Text(text = "ユーザー") },
            selected = currentRoute == Screen.User.route,
            onClick = {
                navController.navigate(Screen.User.route) {
                    popUpTo(Screen.User.route) { inclusive = true }
                }
                viewModel.switchTimeline(DataViewModel.Timeline.USER)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.list_duotone),
                    contentDescription = "List",
                    modifier = Modifier.height(24.dp)
                )
            },
//            label = { Text(text = "設定") },
            selected = false,
            onClick = { }
        )
    }
}
