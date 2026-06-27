package edu.ucne.apiplanetsblayverth.domain.usecase.character

import edu.ucne.apiplanetsblayverth.data.remote.Resource
import edu.ucne.apiplanetsblayverth.domain.model.Character
import edu.ucne.apiplanetsblayverth.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(
        page: Int = 1,
        limit: Int = 10,
        name: String? = null,
        gender: String? = null,
        race: String? = null
    ): Flow<Resource<List<Character>>> {
        return repository.getCharacters(page, limit, name, gender, race)
    }
}