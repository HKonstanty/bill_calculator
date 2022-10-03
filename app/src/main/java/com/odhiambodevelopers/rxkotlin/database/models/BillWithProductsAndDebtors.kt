package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class BillWithProductsAndDebtors(
    @Embedded
    val bill: Bill,
    @Relation(
        entity = Product::class,
        parentColumn = "billId",
        entityColumn = "correspondingBillId"
    )
    val products: List<ProductWithDebtors>
)
