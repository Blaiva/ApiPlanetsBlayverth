package edu.ucne.apiplanetsblayverth.presentation.list.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import coil3.compose.AsyncImage
import edu.ucne.apiplanetsblayverth.domain.model.Character
import edu.ucne.apiplanetsblayverth.domain.model.Planet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {Text("Personajes de Dragon Ball")})
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            ElevatedCard(
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.filterName,
                        onValueChange = {viewModel.onEvent(CharacterListEvent.OnNameChanged(it))},
                        label = {Text("Nombre del personaje")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = state.filterGender,
                            onValueChange = { viewModel.onEvent(CharacterListEvent.OnGenderChanged(it)) },
                            label = { Text("Genero") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = state.filterRace,
                            onValueChange = { viewModel.onEvent(CharacterListEvent.OnRaceChanged(it)) },
                            label = { Text("Raza") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Button(
                        onClick = {viewModel.onEvent(CharacterListEvent.Search)},
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Buscar")
                    }
                }

                if (state.isLoading){
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }

                state.error?.let {
                    Text(
                        text = "Error: $it",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.characters){ character ->
                        CharacterItem(character = character, onClick = {onCharacterClick(character.id)})
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onClick: () -> Unit
){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().clickable{onClick()}
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column{
                Text(
                    character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Raza: ${character.race}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Ki: ${character.ki}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}