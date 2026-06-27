package edu.ucne.apiplanetsblayverth.domain.repository

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.data.remote.dto.PlanetDto
import edu.ucne.apiplanetsblayverth.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Flow<Resource<List<Planet>>>

    suspend fun getPlanetDetail(id: Int): Flow<Resource<Planet>>
}