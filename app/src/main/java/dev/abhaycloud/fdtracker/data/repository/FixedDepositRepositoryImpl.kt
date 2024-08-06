package dev.abhaycloud.fdtracker.data.repository

import dev.abhaycloud.fdtracker.data.local.dao.FixedDepositDao
import dev.abhaycloud.fdtracker.data.local.mapper.toDomain
import dev.abhaycloud.fdtracker.data.local.mapper.toEntity
import dev.abhaycloud.fdtracker.domain.model.FixedDeposit
import dev.abhaycloud.fdtracker.domain.notification.FixedDepositNotificationManager
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import dev.abhaycloud.fdtracker.utils.DateUtils.toDateString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FixedDepositRepositoryImpl(
    private val dao: FixedDepositDao,
    private val notificationManager: FixedDepositNotificationManager
) : FixedDepositRepository {
    override suspend fun addFixedDeposit(fixedDeposit: FixedDeposit): Long {
        val id = dao.insertFixedDeposit(fixedDeposit.toEntity())
        return id
    }

    override fun getFixedDepositById(id: Int): Flow<FixedDeposit?> {
        return dao.getFixedDepositById(id).map { it?.toDomain() }
    }

    override fun getAllFixedDeposits(): Flow<List<FixedDeposit>> {
        return dao.getAllFixedDeposits().map { entityList -> entityList.map { it.toDomain() } }
    }

    override suspend fun updateFixedDeposit(fixedDeposit: FixedDeposit) {
        dao.updateFixedDeposit(fixedDeposit.toEntity())
    }

    override fun getTotalInvestedAmount(): Flow<Double> {
        return dao.getTotalInvestedAmount().map { amount -> amount ?: 0.0 }
    }

    override fun getTotalMaturityAmount(): Flow<Double> {
        return dao.getTotalMaturityAmount().map { amount -> amount ?: 0.0 }
    }

    override suspend fun deleteFixedDeposit(id: Int) {
        dao.deleteFixedDeposit(id)
    }

    override suspend fun deleteAllFixedDeposits() {
        val fixedDeposits = dao.getAllFixedDeposits().first()
        fixedDeposits.forEach {
            notificationManager.cancelNotification(it.id)
        }
        dao.deleteAll()
    }

    override suspend fun rescheduleAlarms() {
        val fixedDeposits = dao.getAllFixedDeposits().first()
        fixedDeposits.forEach { fixedDeposit ->
            val title = "Fixed Deposit Maturity"
            val message = "Your fixed deposit of ${fixedDeposit.principalAmount} is maturing today."
            notificationManager.scheduleNotification(
                fixedDeposit.id,
                title,
                message,
                fixedDeposit.maturityDate,
                0
            )

            val beforeMaturityTitle = "Fixed Deposit Maturity"
            val beforeMaturityMessage =
                "Your fixed deposit of ${fixedDeposit.principalAmount} is maturing on ${fixedDeposit.maturityDate.time.toDateString()}."
            notificationManager.scheduleNotification(
                fixedDeposit.id,
                beforeMaturityTitle,
                beforeMaturityMessage,
                fixedDeposit.maturityDate,
                3
            )
        }
    }

}