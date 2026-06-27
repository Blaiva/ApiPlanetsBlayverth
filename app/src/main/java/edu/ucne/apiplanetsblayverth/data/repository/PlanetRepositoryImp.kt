package edu.ucne.apiplanetsblayverth.data.repository

import edu.ucne.apiplanetsblayverth.data.remote.remotedatasource.PlanetRemoteDataSource
import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.model.Planet
import edu.ucne.apiplanetsblayverth.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanetRepositoryImp @Inject constructor(
    private val remoteDataSource: PlanetRemoteDataSource
) : PlanetRepository {

    override fun getPlanets(
        page: Int,
        limit: Int,
        name: String?,
        isDestroyed: Boolean?
    ): Flow<Resource<List<Planet>>> = flow {

        emit(Resource.Loading())

        val result = remoteDataSource.getPlanets(page, limit, name, isDestroyed)

        result.onSuccess { planetsResponse ->
            val domainPlanets = planetsResponse.items.map { it.toDomain() }
            emit(Resource.Success(domainPlanets))
        }.onFailure { exception ->
            if (!name.isNullOrBlank()) {
                val alternativeResult = remoteDataSource.getPlanets(page = 1, limit = 50, name = null, isDestroyed = null)

                alternativeResult.onSuccess { alternativeResponse ->
                    val filteredList = alternativeResponse.items
                        .filter { it.name.contains(name, ignoreCase = true) }
                        .map { it.toDomain() }
                    emit(Resource.Success(filteredList))
                }.onFailure { nestedException ->
                    emit(Resource.Error("Error en filtro local: ${nestedException.localizedMessage}"))
                }
            } else {
                emit(Resource.Error(exception.message ?: "Error desconocido en el servidor"))
            }
        }
    }

    override suspend fun getPlanetDetail(id: Int): Flow<Resource<Planet>> = flow {
        emit(Resource.Loading())

        val result = remoteDataSource.getPlanetDetail(id)

        result.onSuccess { planetDto ->
            emit(Resource.Success(planetDto.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al obtener detalles del planeta"))
        }
    }
}