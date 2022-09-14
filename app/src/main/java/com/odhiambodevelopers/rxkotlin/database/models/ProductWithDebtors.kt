package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithDebtors(
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "debtorId",
        associateBy = Junction(ProductDebtorCrossRef::class)
    )
    val debtors: List<User>
)
