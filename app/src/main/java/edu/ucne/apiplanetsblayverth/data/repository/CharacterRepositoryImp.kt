package edu.ucne.apiplanetsblayverth.data.repository

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.data.remote.remotedatasource.CharacterRemoteDataSource
import edu.ucne.apiplanetsblayverth.domain.model.Character
import edu.ucne.apiplanetsblayverth.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
): CharacterRepository {
    override fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())

        val result = remoteDataSource.getCharacters(page, limit, name, gender, race)

        result.onSuccess { charactersResponse ->
            val domainCharacters = charactersResponse.items.map { it.toDomain() }
            emit(Resource.Success(domainCharacters))
        }.onFailure { exception ->
            if (!name.isNullOrBlank() || !gender.isNullOrBlank() || !race.isNullOrBlank()) {
                val alternativeResult = remoteDataSource.getCharacters(page = 1, limit = 50, name = null, gender = null, race = null)
                alternativeResult.onSuccess { alternativeResponse ->

                    val filteredList = alternativeResponse.items
                        .filter { item ->
                            val matchesName = name.isNullOrBlank() || item.name.contains(name, ignoreCase = true)
                            val matchesGender = gender.isNullOrBlank() || item.gender.equals(gender, ignoreCase = true)
                            val matchesRace = race.isNullOrBlank() || item.race.contains(race, ignoreCase = true)

                            matchesName && matchesGender && matchesRace
                        }
                        .map { it.toDomain() }

                    emit(Resource.Success(filteredList))
                }.onFailure { nestedException ->
                    emit(Resource.Error("Error en filtro local: ${nestedException.localizedMessage}"))
                }
            }else{
                emit(Resource.Error(exception.message ?: "Error desconocido en el servidor"))
            }
        }
    }

    override suspend fun getCharacterDetail(id: Int): Flow<Resource<Character>> = flow {
        emit(Resource.Loading())

        val result = remoteDataSource.getCharacterDetail(id)

        result.onSuccess { characterDto ->
            emit(Resource.Success(characterDto.toDomain()))
        }.onFailure { exception ->
            emit(Resource.Error(exception.message ?: "Error al obtener detalles del personaje"))
        }
    }
}