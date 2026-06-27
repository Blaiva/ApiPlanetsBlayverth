package edu.ucne.apiplanetsblayverth.data.remote.remotedatasource

import coil3.network.HttpException
import edu.ucne.apiplanetsblayverth.data.remote.DragonBallApi
import edu.ucne.apiplanetsblayverth.data.remote.dto.character.CharacterDto
import edu.ucne.apiplanetsblayverth.data.remote.dto.character.CharactersResponseDto
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val api: DragonBallApi
) {
    suspend fun getCharacters(
        page: Int,
        limit: Int,
        name: String?,
        gender: String?,
        race: String?
    ): Result<CharactersResponseDto>{
        return try {
            val response = api.getCharacters(page, limit, name, gender, race)
            if(response.isSuccessful && response.body() != null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error de red ${response.code()}: ${response.message()}"))
            }
        }catch (e: HttpException) {
            Result.failure(Exception("Error de servidor", e))
        }catch (e: Exception){
            Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun getCharacterDetail(id: Int): Result<CharacterDto>{
        return try {
            val response = api.getCharacterDetail(id)
            if(response.isSuccessful && response.body() != null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Personaje no encontrado"))
            }
        }catch (e: HttpException){
            Result.failure(Exception("Error de servidor", e))
        }catch (e: Exception){
            Result.failure(Exception("Error desconocido", e))
        }
    }
}