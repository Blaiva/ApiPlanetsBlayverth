package edu.ucne.apiplanetsblayverth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Room
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.apiplanetsblayverth.presentation.navigation.AppNavHost
import edu.ucne.apiplanetsblayverth.presentation.navigation.Screen
import edu.ucne.apiplanetsblayverth.ui.theme.ApiPlanetsBlayverthTheme
import androidx.navigation.NavDestination.Companion.hasRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiPlanetsBlayverthTheme {
                val navController = rememberNavController()
                val items = listOf(
                    TopLevelRoute("Planetas", Screen.PlanetList, Icons.Default.Place),
                    TopLevelRoute("Personajes", Screen.CharacterList, Icons.Default.Person)
                )
                Scaffold(
                    bottomBar = {
                        NavigationBar() {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            items.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icono, contentDescription = item.nombre) },
                                    label = {Text(item.nombre)},
                                    selected = currentDestination?.hierarchy?.any { it.hasRoute(item.ruta::class) } == true ,
                                    onClick = {
                                        navController.navigate(item.ruta){
                                            popUpTo (navController.graph.findStartDestination().id ){
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) {innerPadding ->
                    AppNavHost(
                        navHostController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

data class TopLevelRoute<T : Any>(
    val nombre: String,
    val ruta: T,
    val icono: androidx.compose.ui.graphics.vector.ImageVector
)
