package dev.abhaycloud.fdtracker.domain.usecase

import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalInvestedAmountUseCase @Inject constructor(private val repository: FixedDepositRepository) {
    fun execute(): Flow<Double> {
        return repository.getTotalInvestedAmount()
    }
}