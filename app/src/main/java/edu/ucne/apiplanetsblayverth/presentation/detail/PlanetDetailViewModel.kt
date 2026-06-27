package edu.ucne.apiplanetsblayverth.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.usecase.GetPlanetDetailUseCase
import edu.ucne.apiplanetsblayverth.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val getPlanetDetailUseCase: GetPlanetDetailUseCase,
    savedState: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(PlanetDetailUiState())
    val state = _state.asStateFlow()

    init {
        val args = savedState.toRoute<Screen.PlanetDetail>()
        loadPlanet(args.id)
    }

    private fun loadPlanet(id: Int){
        viewModelScope.launch {
            getPlanetDetailUseCase(id).collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                planet = result.data,
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