package com.mobproassesment2.actilog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobproassesment2.actilog.ui.screen.DetailScreen
import com.mobproassesment2.actilog.ui.screen.KEY_ID_KEGIATAN
import com.mobproassesment2.actilog.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route){
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_KEGIATAN){ type = NavType.LongType }
            )
        ){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_KEGIATAN)
            DetailScreen(navController,id)
        }
    }
}