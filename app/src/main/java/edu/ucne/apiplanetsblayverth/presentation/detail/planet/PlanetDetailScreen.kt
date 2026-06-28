package edu.ucne.apiplanetsblayverth.presentation.detail.planet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(
    viewModel: PlanetDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Planeta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atras"
                        )
                    }
                }
            )
        }
    ) {padding ->
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier.padding(padding))
        }

        state.planet?.let { planet ->
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                AsyncImage(
                    model = planet.image,
                    contentDescription = planet.name,
                    modifier = Modifier.fillMaxWidth().height(240.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(planet.name, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Estado: ${if(planet.isDestroyed) "Destruido" else "Intacto"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Descripcion: ", style = MaterialTheme.typography.titleMedium)
                Text(planet.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}