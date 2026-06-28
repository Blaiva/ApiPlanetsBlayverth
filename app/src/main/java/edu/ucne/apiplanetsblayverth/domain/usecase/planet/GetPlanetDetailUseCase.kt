package edu.ucne.apiplanetsblayverth.domain.usecase.planet

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.model.Planet
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Planet>> {
        return repository.getPlanetDetail(id)
    }
}