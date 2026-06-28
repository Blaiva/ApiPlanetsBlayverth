package edu.ucne.apiplanetsblayverth.presentation.list.character

sealed interface CharacterListEvent {
    data class OnNameChanged(val name: String) : CharacterListEvent
    data class OnGenderChanged(val gender: String) : CharacterListEvent
    data class OnRaceChanged(val race: String) : CharacterListEvent
    data object Search: CharacterListEvent
}