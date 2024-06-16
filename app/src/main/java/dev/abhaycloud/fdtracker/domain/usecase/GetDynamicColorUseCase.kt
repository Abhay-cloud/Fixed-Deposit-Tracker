package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDynamicColorUseCase @Inject constructor(private val repository: PreferencesRepository) {
     fun execute(): Flow<Boolean> {
        return repository.dynamicColor
    }
}