package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "debtorId"])
data class ProductDebtorCrossRef(
    val productId: Long,
    val debtorId: Long
)
