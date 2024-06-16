package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(private val repository: PreferencesRepository) {
    suspend fun execute(enabled: Boolean) {
        repository.setDarkMode(enabled)
    }
}