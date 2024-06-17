package dev.abhaycloud.fdtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.abhaycloud.fdtracker.data.local.entity.FixedDepositEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FixedDepositDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixedDeposit(fixedDeposit: FixedDepositEntity): Long

    @Query("SELECT * FROM fixed_deposit WHERE id = :id")
    suspend fun getFixedDepositById(id: Int): FixedDepositEntity?

    @Query("SELECT * FROM fixed_deposit ORDER BY createdAt DESC")
    fun getAllFixedDeposits(): Flow<List<FixedDepositEntity>>

    @Update
    suspend fun updateFixedDeposit(fixedDeposit: FixedDepositEntity)

    @Query("SELECT SUM(principalAmount) FROM fixed_deposit")
    fun getTotalInvestedAmount(): Flow<Double?>

    @Query("SELECT SUM(maturityAmount) FROM fixed_deposit")
    fun getTotalMaturityAmount(): Flow<Double?>

    @Query("DELETE FROM fixed_deposit WHERE id = :id")
    suspend fun deleteFixedDeposit(id: Int)

    @Query("DELETE FROM fixed_deposit")
    suspend fun deleteAll()
}