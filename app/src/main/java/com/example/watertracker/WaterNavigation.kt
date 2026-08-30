package com.example.watertracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watertracker.ui.HistoryScreen
import com.example.watertracker.ui.WaterScreen

@Composable
fun WaterNavigaton(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main"){
            WaterScreen(onHistoryClick = { navController.navigate("history") })
        }
        composable("history"){
            HistoryScreen(onBackClick = { navController.popBackStack() })
        }
    }
}