package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import javax.inject.Inject

class UpdateFixedDepositUseCase @Inject constructor(private val repository: FixedDepositRepository) {
    suspend fun execute(fixedDeposit: FixedDeposit) {
        repository.updateFixedDeposit(fixedDeposit)
    }
}