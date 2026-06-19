package edu.ucne.apiplanetsblayverth.remote

import com.google.gson.annotations.SerializedName

data class DragonBallResponse (
    @SerializedName("items") val items: List<CharacterDto>
)