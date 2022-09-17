package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val correspondingBillId: Long,
    val productName: String,
    val productPrize: Double,
    val productAmount: Int,
) {
    @Ignore
    var visibility: Boolean = false
}
