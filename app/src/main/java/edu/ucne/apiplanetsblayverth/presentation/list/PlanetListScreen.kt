package edu.ucne.apiplanetsblayverth.presentation.list

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import edu.ucne.apiplanetsblayverth.domain.model.Planet
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListScreen(
    viewModel: PlanetListViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Planetas de Dragon Ball") })
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
                        onValueChange = {viewModel.onEvent(PlanetListEvent.UpdateFilters(it, state.filterIsDestroyed))},
                        label = {Text("Nombre del Planeta")},
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {viewModel.onEvent(PlanetListEvent.Search)},
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Buscar")
                    }
                }
            }

            if (state.isLoading){
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
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
                items(state.planets){planet ->
                    PlanetItem(planet = planet, onClick = {onPlanetClick(planet.id)})
                }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
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
                model = planet.image,
                contentDescription = planet.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    planet.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (planet.isDestroyed) "Destruido" else "Intacto",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
