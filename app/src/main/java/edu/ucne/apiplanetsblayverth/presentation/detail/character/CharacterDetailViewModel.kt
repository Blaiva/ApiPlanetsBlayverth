package edu.ucne.apiplanetsblayverth.presentation.detail.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.usecase.character.GetCharacterDetailUseCase
import edu.ucne.apiplanetsblayverth.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    savedState: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailUiState())
    val state = _state.asStateFlow()

    init {
        val args = savedState.toRoute<Screen.CharacterDetail>()
        loadCharacter(args.id)
    }

    private fun loadCharacter(id: Int){
        viewModelScope.launch {
            getCharacterDetailUseCase(id).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                character = result.data,
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}