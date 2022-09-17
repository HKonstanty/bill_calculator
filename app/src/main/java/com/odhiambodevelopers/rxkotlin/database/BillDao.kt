package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.BillWithProductsAndDebtors

@Dao
interface BillDao {

    @Query("SELECT * FROM Bill")
    fun getBills(): List<Bill>

    @Insert
    fun insertBill(vararg bill: Bill): List<Long>

    @Update
    fun updateBill(bill: Bill)

    @Delete
    fun deleteBill(vararg bills: Bill)

    @Transaction
    @Query("SELECT * FROM Bill")
    fun getBillsWithProductsAndDebtors(): List<BillWithProductsAndDebtors>
}