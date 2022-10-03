package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BillWithOwnerAndDebtors(
    @Embedded
    val bill: Bill,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "userId"
    )
    val user: User,
    @Relation(
        parentColumn = "billId",
        entityColumn = "userId",
        associateBy = Junction(BillDebtorCrossRef::class)
    )
    val debtors: List<User>

)
