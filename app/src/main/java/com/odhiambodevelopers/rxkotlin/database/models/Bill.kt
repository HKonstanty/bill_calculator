package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true)
    val billId: Long,
    val ownerId: Long,
    val billName: String,
    val category: String,
    val prize: Double,
    val date: Long
)
