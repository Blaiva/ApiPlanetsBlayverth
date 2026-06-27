package edu.ucne.apiplanetsblayverth.data.remote.dto.planet

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanetsResponseDto(
    val items: List<PlanetDto>
)