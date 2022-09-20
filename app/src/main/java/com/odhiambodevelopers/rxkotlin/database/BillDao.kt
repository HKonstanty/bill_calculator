package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*
import com.odhiambodevelopers.rxkotlin.database.models.*

@Dao
interface BillDao {

    @Query("SELECT * FROM Bill")
    fun getBills(): List<Bill>

    @Query("SELECT * FROM Bill WHERE billId = :billId")
    fun getBillWithId(billId: Long): Bill

    @Insert
    fun insertBill(vararg bill: Bill): List<Long>

    @Insert
    fun insertBillDebtorCrossRef(billDebtorCrossRef: BillDebtorCrossRef)

    @Update
    fun updateBill(bill: Bill)

    @Delete
    fun deleteBill(vararg bills: Bill)

    @Query("DELETE FROM BillDebtorCrossRef WHERE userId = :debtorId")
    fun deleteDebtorFromBill(debtorId: Long)

    @Query("DELETE FROM BillDebtorCrossRef WHERE billId = :billId")
    fun deleteBillDebtorCrossRef(billId: Long)

    @Transaction
    fun deleteBillAndDebtors(bill: Bill) {
        deleteBill(bill)
        deleteBillDebtorCrossRef(billId = bill.billId)
    }

    @Transaction
    @Query("SELECT * FROM Bill")
    fun getBillsWithProductsAndDebtors(): List<BillWithProductsAndDebtors>

    @Transaction
    @Query("SELECT * FROM Bill WHERE billId = :billId")
    fun getBillByBillIdWithOwnerAndDebtors(billId: Long):  BillWithOwnerAndDebtors

    @Transaction
    @Query("SELECT * FROM Bill")
    fun getBillWithOwnerAndDebtors(billId: Long): List<BillWithOwnerAndDebtors>

    @Transaction
    @Query("SElECT * FROM Bill WHERE billId = :billId")
    fun getBillByBillIdWithOwnerAndDebtorsAndProductsWithDebtors(billId: Long): BillWithOwnerAndDebtorsWithProductsAndDebtors
}