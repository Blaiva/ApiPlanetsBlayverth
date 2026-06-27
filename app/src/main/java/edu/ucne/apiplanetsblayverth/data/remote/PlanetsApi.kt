package edu.ucne.apiplanetsblayverth.data.remote

import edu.ucne.apiplanetsblayverth.data.remote.dto.planet.PlanetDto
import edu.ucne.apiplanetsblayverth.data.remote.dto.planet.PlanetsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanetsApi {
    @GET("planets")
    suspend fun getPlanets(
        @Query("page") page : Int,
        @Query("limit") limit : Int,
        @Query("name") name : String?,
        @Query("isDestroyed") isDestroyed : Boolean?
    ): Response<PlanetsResponseDto>

    @GET("planets/{id}")
    suspend fun getPlanetDetail(
        @Path("id") id: Int
    ): Response<PlanetDto>
}