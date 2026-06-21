package edu.ucne.apiplanetsblayverth.domain.usecase

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.data.remote.dto.PlanetDto
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int): Resource<PlanetDto>{
        return repository.getPlanetDetail(id)
    }
}