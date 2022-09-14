package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithBills(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val bills: List<Bill>
)
