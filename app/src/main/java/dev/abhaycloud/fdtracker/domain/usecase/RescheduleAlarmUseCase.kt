package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import javax.inject.Inject

class RescheduleAlarmUseCase @Inject constructor(private val repository: FixedDepositRepository) {
    suspend fun execute() {
        repository.rescheduleAlarms()
    }
}