package dev.abhaycloud.fdtracker.data.repository

import android.util.Log
import dev.abhaycloud.fdtracker.data.local.dao.FixedDepositDao
import dev.abhaycloud.fdtracker.data.local.mapper.toDomain
import dev.abhaycloud.fdtracker.data.local.mapper.toEntity
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FixedDepositRepositoryImpl(private val dao: FixedDepositDao) : FixedDepositRepository {
    override suspend fun addFixedDeposit(fixedDeposit: FixedDeposit): Long {
        val id = dao.insertFixedDeposit(fixedDeposit.toEntity())
        return id
    }

    //    override suspend fun getFixedDepositById(id: Int): FixedDeposit? {
//        TODO("Not yet implemented")
//    }
//
    override fun getAllFixedDeposits(): Flow<List<FixedDeposit>> {
        return dao.getAllFixedDeposits().map { entityList -> entityList.map { it.toDomain() } }
    }

    override suspend fun updateFixedDeposit(fixedDeposit: FixedDeposit) {
        dao.updateFixedDeposit(fixedDeposit.toEntity())
    }

    override fun getTotalInvestedAmount(): Flow<Double> {
        return dao.getTotalInvestedAmount().map { amount -> amount ?: 0.0 }
    }

    override suspend fun deleteFixedDeposit(id: Int) {
        dao.deleteFixedDeposit(id)
    }

    override suspend fun deleteAllFixedDeposits() {
        dao.deleteAll()
    }
}