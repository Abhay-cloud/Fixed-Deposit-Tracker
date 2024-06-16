package dev.abhaycloud.fdtracker.domain.repository

import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import kotlinx.coroutines.flow.Flow

interface FixedDepositRepository {
    suspend fun addFixedDeposit(fixedDeposit: FixedDeposit): Long
//    suspend fun getFixedDepositById(id: Int): FixedDeposit?
    fun getAllFixedDeposits(): Flow<List<FixedDeposit>>
    suspend fun updateFixedDeposit(fixedDeposit: FixedDeposit)
    fun getTotalInvestedAmount(): Flow<Double>
    suspend fun deleteFixedDeposit(id: Int)
}