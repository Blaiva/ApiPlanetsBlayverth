package edu.ucne.apiplanetsblayverth.presentation.detail

import edu.ucne.apiplanetsblayverth.domain.model.Planet

data class PlanetDetailUiState(
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
)
