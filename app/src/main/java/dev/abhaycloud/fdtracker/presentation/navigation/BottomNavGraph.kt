package dev.abhaycloud.fdtracker.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.abhaycloud.fdtracker.presentation.ui.components.IconWrapper

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    val items = remember {
        listOf(
            BottomNavDestinations.HomeScreen,
            BottomNavDestinations.CalculatorScreen,
            BottomNavDestinations.SettingsScreen,
        )
    }
    NavigationBar(containerColor = Color.Black) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.route
            val navItemClick: () -> Unit = remember {
                {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
            NavigationBarItem(
                selected = isSelected,
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = navItemClick
//                {
//                    if (!isSelected) {
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                }
                ,
                label = {
                    Text(text = item.title, color = Color.White)
                },
                icon = {
//                    Icon(
//                        painter = painterResource(id = item.icon),
//                        contentDescription = item.title,
//                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White
//                    )
                    IconWrapper(resource = item.icon, tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else Color.White)
                })
        }
    }
}