package com.odhiambodevelopers.rxkotlin.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.odhiambodevelopers.rxkotlin.database.models.Product
import com.odhiambodevelopers.rxkotlin.database.models.ProductDebtorCrossRef
import com.odhiambodevelopers.rxkotlin.database.models.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var productDao: ProductDao
    private lateinit var billDao: BillDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        userDao = db.userDao
        productDao = db.productDao
        billDao = db.billDao
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }

    @Test
    fun writeAndReadProductTest() {
        val product = Product(1,0, "name", 12.55, 5)
        productDao.insertProduct(product)
        assertThat(productDao.getProducts().size).isAtLeast(1)
        assertThat(productDao.getProducts()[0]).isEqualTo(product)
    }

    @Test
    fun writeAndReadProductWithDebtors() {
        val debtor1 = User(1, "name", "surname", "email")
        val debtor2 = User(2, "name", "surname", "email")
        val userId1 = userDao.insertUser(debtor1)
        val userId2 = userDao.insertUser(debtor2)
        val product = Product(1,0, "name", 12.55, 5)
        val productId = productDao.insertProduct(product)
        val prodDebCrossRef1 = ProductDebtorCrossRef(productId[0], userId1)
        val prodDebCrossRef2 = ProductDebtorCrossRef(productId[0], userId2)
        productDao.insertProductDebtorCrossRef(prodDebCrossRef1)
        productDao.insertProductDebtorCrossRef(prodDebCrossRef2)

        assertThat(productDao.getProductsWithDebtors().size).isEqualTo(1)
        assertThat(productDao.getProductsWithDebtors()[0].debtors.size).isEqualTo(2)
    }
}