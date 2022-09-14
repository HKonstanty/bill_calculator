package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.UserWithBills

@Dao
interface BillDao {

    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithBills(): List<UserWithBills>

    @Query("SELECT * FROM bills")
    fun getBills(): List<Bill>

    @Insert
    fun insertBill(vararg bill: Bill)

    @Update
    fun updateBill(bill: Bill)

    @Delete
    fun deleteBill(vararg bills: Bill)
}