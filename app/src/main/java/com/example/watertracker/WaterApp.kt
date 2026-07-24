package com.example.watertracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WaterApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main"){
        composable("main"){
            WaterScreen(onHistoryClick = {navController.navigate("history")})
        }
        composable("history"){
            HistoryScreen(onBackClick = {navController.navigate("main")})
        }
    }
}