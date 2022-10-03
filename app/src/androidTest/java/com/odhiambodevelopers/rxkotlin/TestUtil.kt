package com.odhiambodevelopers.rxkotlin

import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.database.models.Bill

object TestUtil {

    fun createUser(): User {
        return User(0, "name", "surname", "email")
    }

    fun createProduct(correspondingBillId: Long = 0): Product {
        return Product(
            productId = 0,
            correspondingBillId = correspondingBillId,
            productName = "productName",
            productAmount = 0,
            productPrize = 0.00
        )
    }

    fun createBill(billOwnerId: Long = 0): Bill {
        return Bill(
            billId = 0,
            ownerId = billOwnerId,
            billName = "billName",
            prize = 0.00,
            date = 0.00.toLong(),
            category = "category"
        )
    }
}