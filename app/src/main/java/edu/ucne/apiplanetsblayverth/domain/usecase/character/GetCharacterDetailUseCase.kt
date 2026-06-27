package edu.ucne.apiplanetsblayverth.domain.usecase.character

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.model.Character
import edu.ucne.apiplanetsblayverth.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Character>>{
        return repository.getCharacterDetail(id)
    }
}