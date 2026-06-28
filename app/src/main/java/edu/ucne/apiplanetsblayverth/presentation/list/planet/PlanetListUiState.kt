package edu.ucne.apiplanetsblayverth.presentation.list.planet

import edu.ucne.apiplanetsblayverth.domain.model.Planet

data class PlanetListUiState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val error: String? = null,
    val filterName: String = "",
    val filterIsDestroyed: Boolean? = null
)
