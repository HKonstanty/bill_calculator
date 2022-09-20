package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class BillWithOwner(
    @Embedded
    val bill: Bill,
    @Relation(
        parentColumn = "billId",
        entityColumn = "ownerId"
    )
    val owner: User
)
