package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Entity

@Entity(primaryKeys = ["productId", "userId"])
data class ProductDebtorCrossRef(
    val productId: Long,
    val userId: Long
)
