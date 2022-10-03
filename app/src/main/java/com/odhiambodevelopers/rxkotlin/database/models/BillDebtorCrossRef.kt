package com.odhiambodevelopers.rxkotlin.database.models

import androidx.room.Entity

<<<<<<< HEAD

=======
>>>>>>> master
@Entity(primaryKeys = ["billId", "userId"])
data class BillDebtorCrossRef(
    val billId: Long,
    val userId: Long
)
