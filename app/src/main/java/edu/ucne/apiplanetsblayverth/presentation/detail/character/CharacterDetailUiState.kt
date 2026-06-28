package edu.ucne.apiplanetsblayverth.presentation.detail.character

import edu.ucne.apiplanetsblayverth.domain.model.Character

data class CharacterDetailUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)