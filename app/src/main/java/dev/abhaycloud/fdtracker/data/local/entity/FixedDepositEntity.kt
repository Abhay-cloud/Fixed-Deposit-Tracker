package dev.abhaycloud.fdtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "fixed_deposit")
data class FixedDepositEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val principalAmount: Double,
    val maturityAmount: Double,
    val tenure: Int,
    val interestRate: Double,
    val startDate: Date,
    val maturityDate: Date,
    val createdAt: Date,
    val notes: String?
)
