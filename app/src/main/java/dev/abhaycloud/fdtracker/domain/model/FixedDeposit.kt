package dev.abhaycloud.fdtracker.domain.model

import java.util.Date

data class FixedDeposit(
    val id: Int,
    val bankName: String,
    val principalAmount: Double,
    val maturityAmount: Double,
    val tenure: Int,
    val interestRate: Double,
    val startDate: Date,
    val maturityDate: Date,
    val createdAt: Date,
    val notes: String?
)