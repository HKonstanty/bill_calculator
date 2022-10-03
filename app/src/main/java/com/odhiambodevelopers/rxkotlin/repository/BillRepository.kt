package com.odhiambodevelopers.rxkotlin.repository

import com.odhiambodevelopers.rxkotlin.database.BillDao
import com.odhiambodevelopers.rxkotlin.database.models.*

class BillRepository(private val billDao: BillDao) {

    fun getBills() = billDao.getBills()

    fun getBillWithId(billId: Long): Bill {
        return billDao.getBillWithId(billId)
    }

    fun getBillsWithProductsAndDebtors(): List<BillWithProductsAndDebtors> {
        return billDao.getBillsWithProductsAndDebtors()
    }

    fun getBillByBillIdWithOwnerAndDebtors(billId: Long): BillWithOwnerAndDebtors {
        return billDao.getBillByBillIdWithOwnerAndDebtors(billId)
    }

    fun getBillByBillIdWithOwnerAndDebtorsAndProductsWithDebtors(billId: Long): BillWithOwnerAndDebtorsWithProductsAndDebtors {
        return billDao.getBillByBillIdWithOwnerAndDebtorsAndProductsWithDebtors(billId)
    }

    fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill)
    }

    fun insertBill(bill: Bill): List<Long> {
        return billDao.insertBill(bill)
    }

    suspend fun insertBillWithDebtors(bill: Bill, debtors: List<User>): Long {
        val billId = insertBill(bill)[0]
        debtors.forEach {
            billDao.insertBillDebtorCrossRef(BillDebtorCrossRef(billId, it.userId))
        }
        return billId
    }

    fun updateBill(bill: Bill) {
        billDao.updateBill(bill)
    }
}