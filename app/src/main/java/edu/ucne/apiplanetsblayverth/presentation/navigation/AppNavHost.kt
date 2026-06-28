package edu.ucne.apiplanetsblayverth.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ucne.apiplanetsblayverth.presentation.detail.character.CharacterDetailScreen
import edu.ucne.apiplanetsblayverth.presentation.detail.planet.PlanetDetailScreen
import edu.ucne.apiplanetsblayverth.presentation.list.character.CharacterListScreen
import edu.ucne.apiplanetsblayverth.presentation.list.planet.PlanetListScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.PlanetList,
        modifier = Modifier.padding(innerPadding)
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

        composable<Screen.CharacterList>{
            CharacterListScreen(
                onCharacterClick = {characterId ->
                    navHostController.navigate(Screen.CharacterDetail(id = characterId))
                }
            )
        }

        composable<Screen.CharacterDetail>{
            CharacterDetailScreen(onBack = {navHostController.navigateUp()})
        }
    }
}