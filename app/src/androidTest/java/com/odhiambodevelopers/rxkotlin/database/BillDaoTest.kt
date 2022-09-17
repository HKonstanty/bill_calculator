package com.odhiambodevelopers.rxkotlin.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductDebtorCrossRef
import com.odhiambodevelopers.rxkotlin.database.models.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class BillDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var billDao: BillDao
    private lateinit var userDao: UserDao
    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        billDao = db.billDao
        userDao = db.userDao
        productDao = db.productDao
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }

    @Test
    fun writeAndReadBillTest() {
        val billsListSize = billDao.getBills().size
        val bill = Bill(0, 0, "name", "category", 12.59, (0.0).toLong())
        billDao.insertBill(bill)
        val billsListSizeAfterInsert = billDao.getBills().size
        assertThat(billsListSize).isLessThan(billsListSizeAfterInsert)
    }

    @Test
    fun writeAndReadBillWithProductsAndDebtors() {
        // Add users
        val user1 = User(1, "name", "surname", "email")
        val user2 = User(2, "name", "surname", "email")
        userDao.insertUser(user1)
        userDao.insertUser(user2)

        // Add bill
        val bill1 = Bill(1, 1, "name", "category", 12.59, (0.0).toLong())
        val bill2 = Bill(2, 1, "name", "category", 12.59, (0.0).toLong())
        billDao.insertBill(bill1, bill2)

        // Add product
        val product1 = Product(1,1, "name", 12.55, 5)
        val product2 = Product(2,1, "name2", 1.55, 3)
        val product3 = Product(3,1, "name3", 2.55, 1)
        productDao.insertProduct(product1, product2, product3)

        // Add product - debtors cross ref
        val prodDebCrossRef1 = ProductDebtorCrossRef(1, 1)
        val prodDebCrossRef2 = ProductDebtorCrossRef(1, 2)
        val prodDebCrossRef3 = ProductDebtorCrossRef(2, 2)
        val prodDebCrossRef4 = ProductDebtorCrossRef(3, 2)
        productDao.insertProductDebtorCrossRef(prodDebCrossRef1, prodDebCrossRef2, prodDebCrossRef3, prodDebCrossRef4)

        assertThat(billDao.getBillsWithProductsAndDebtors().size).isEqualTo(2)
        assertThat(billDao.getBillsWithProductsAndDebtors()[0].bill).isEqualTo(bill1)
        assertThat(billDao.getBillsWithProductsAndDebtors()[0].products.size).isEqualTo(3)
    }
}