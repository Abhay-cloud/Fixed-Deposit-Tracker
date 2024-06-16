package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFixedDepositUseCase @Inject constructor(private val repository: FixedDepositRepository) {
     fun execute(): Flow<List<FixedDeposit>> {
        return repository.getAllFixedDeposits()
    }
}