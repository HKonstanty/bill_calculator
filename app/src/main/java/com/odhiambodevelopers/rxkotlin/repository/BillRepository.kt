package com.odhiambodevelopers.rxkotlin.repository

import com.odhiambodevelopers.rxkotlin.database.BillDao
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.BillDebtorCrossRef
import com.odhiambodevelopers.rxkotlin.database.models.User

class BillRepository(private val billDao: BillDao) {

    fun getBills() = billDao.getBills()

    fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill)
    }

    fun insertBill(bill: Bill): List<Long> {
        return billDao.insertBill(bill)
    }

    suspend fun insertBillWithDebtors(bill: Bill, debtors: List<User>) {
        val billId = insertBill(bill)[0]
        debtors.forEach {
            billDao.insertBillDebtorCrossRef(BillDebtorCrossRef(billId, it.userId))
        }
    }

    fun updateBill(bill: Bill) {
        billDao.updateBill(bill)
    }
}