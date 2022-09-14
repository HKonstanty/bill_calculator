package com.odhiambodevelopers.rxkotlin.repository

import com.odhiambodevelopers.rxkotlin.database.BillDao
import com.odhiambodevelopers.rxkotlin.database.models.Bill

class BillRepository(private val billDao: BillDao) {

    fun getBillsWithOwner() = billDao.getUsersWithBills()

    fun getBills() = billDao.getBills()

    fun deleteBill(bill: Bill) {
        billDao.deleteBill(bill)
    }

    fun insertBill(bill: Bill) {
        billDao.insertBill(bill)
    }

    fun updateBill(bill: Bill) {
        billDao.updateBill(bill)
    }
}