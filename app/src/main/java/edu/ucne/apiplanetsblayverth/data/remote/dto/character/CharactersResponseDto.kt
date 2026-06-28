package edu.ucne.apiplanetsblayverth.data.remote.dto.character

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersResponseDto (
    val items: List<CharacterDto>
)