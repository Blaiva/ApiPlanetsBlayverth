package edu.ucne.apiplanetsblayverth.presentation.detail.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del personaje") },
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
    ) { padding ->
        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier.padding(padding))
        }

        state.character?.let { character ->
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    modifier = Modifier.fillMaxWidth().height(240.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(character.name, style = MaterialTheme.typography.headlineMedium)

                Text("Genero: ${character.gender}", style = MaterialTheme.typography.bodyLarge)
                Text("Raza: ${character.race}", style = MaterialTheme.typography.bodyLarge)
                Text("Ki: ${character.ki}", style = MaterialTheme.typography.bodyLarge)
                Text("Máximo Ki: ${character.maxKi}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Descripcion:", style = MaterialTheme.typography.titleMedium)
                Text(character.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}