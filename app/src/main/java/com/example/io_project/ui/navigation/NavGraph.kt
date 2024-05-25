package com.example.io_project.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.io_project.ui.screens.dialogs.AddActivityDialog
import com.example.io_project.ui.screens.archivescreen.ArchiveScreen
import com.example.io_project.ui.screens.profilescreen.ProfileScreen
import com.example.io_project.ui.screens.authscreen.AuthScreen
import com.example.io_project.ui.screens.calendarscreen.CalendarScreen
import com.example.io_project.ui.screens.goalsscreen.GoalsScreen
import com.example.io_project.ui.screens.groupscreen.GroupScreen
import com.example.io_project.ui.screens.homescreen.HomeScreen
import com.example.io_project.ui.screens.socialscreen.SocialScreen
import com.example.io_project.ui.screens.splashscreeen.SplashScreen
import com.example.io_project.ui.screens.statsscreen.StatsScreen
import com.example.io_project.ui.screens.taskscreen.TasksScreen

@Composable
@ExperimentalAnimationApi
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        // TODO:
        // 1) Zrobic nawigacje do pozostalych ekranow
        // 2) Ustawic HomeScreen na startowy gdy uzytkownik jest juz zalogowany
        //    (to moze wymagac trzymania stanu zalogowania w viewmodelu DO SPRAWDZENIA)
        // 3) Dodac CONSTY zeby podmienic stringi uzywane do nawigacji (zgodnie z SOLID)

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.CalendarScreen.route) {
            CalendarScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.ArchiveScreen.route) {
            ArchiveScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.GoalsScreen.route) {
            GoalsScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.GroupScreen.route) {
            GroupScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.TasksScreen.route) {
            TasksScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.SocialScreen.route) {
            SocialScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.StatsScreen.route) {
            StatsScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navigateTo = { navController.navigate(it) })
        }
        dialog(route = Screen.AddActivityDialog.route) {
            AddActivityDialog(navigateBack = { navController.popBackStack() })
        }
    }
}

