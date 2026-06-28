package edu.ucne.apiplanetsblayverth.data.remote.dto.character

import com.squareup.moshi.JsonClass
import edu.ucne.apiplanetsblayverth.domain.model.Character

@JsonClass(generateAdapter = true)
data class CharacterDto(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val image: String
){
    fun toDomain() = Character(
        id, name, ki, maxKi, race, gender, description, image
    )
}