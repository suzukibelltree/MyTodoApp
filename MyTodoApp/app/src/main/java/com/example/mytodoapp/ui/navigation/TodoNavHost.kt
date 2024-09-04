package com.example.mytodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mytodoapp.ui.home.HomeDestination
import com.example.mytodoapp.ui.home.HomeScreen
import com.example.mytodoapp.ui.item.ItemEditDestination
import com.example.mytodoapp.ui.item.ItemEditScreen
import com.example.mytodoapp.ui.item.ItemEntryDestination
import com.example.mytodoapp.ui.item.ItemEntryScreen


@Composable
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(navigateToItemEntry = {
                navController.navigate(ItemEntryDestination.route)
            }, navigateToItemUpdate = {
                navController.navigate("${ItemEditDestination.route}/${it}")
            })
        }

        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(navigateBack = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            }, onNavigateUp = {
                navController.navigateUp()
            })
        }

        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = {
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}