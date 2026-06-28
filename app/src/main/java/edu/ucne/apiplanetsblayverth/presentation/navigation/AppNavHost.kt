package edu.ucne.apiplanetsblayverth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.apiplanetsblayverth.presentation.detail.planet.PlanetDetailScreen
import edu.ucne.apiplanetsblayverth.presentation.list.planet.PlanetListScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlanetList
    ){
        composable<Screen.PlanetList> {
            PlanetListScreen(
                onPlanetClick = {planetId ->
                    navHostController.navigate(Screen.PlanetDetail(id = planetId))
                }
            )
        }

        composable<Screen.PlanetDetail> {
            PlanetDetailScreen(onBack = {navHostController.navigateUp()})
        }
    }
}