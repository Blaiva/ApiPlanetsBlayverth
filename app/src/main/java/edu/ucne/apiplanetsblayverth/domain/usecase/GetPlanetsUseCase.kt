package edu.ucne.apiplanetsblayverth.domain.usecase

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.data.remote.dto.PlanetDto
import edu.ucne.apiplanetsblayverth.domain.model.Planet
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null,
        isDestroyed: Boolean? = null
    ): Flow<Resource<List<Planet>>> {
        return repository.getPlanets(page, limit, name, isDestroyed)
    }
}