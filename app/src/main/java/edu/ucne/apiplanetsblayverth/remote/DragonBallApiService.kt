package edu.ucne.apiplanetsblayverth.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface DragonBallApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): DragonBallResponse
}