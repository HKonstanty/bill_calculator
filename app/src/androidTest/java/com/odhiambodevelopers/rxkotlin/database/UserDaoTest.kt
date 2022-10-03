package com.odhiambodevelopers.rxkotlin.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var billDao:  BillDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        userDao = db.userDao
        billDao = db.billDao
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }

    @Test
    fun writeAndReadUserTest() {
        val user = User(1, "name", "surname", "email")
        val usersListSize = userDao.getAllUsers().size
        userDao.insertUser(user)
        val usersListSizeAfterInsert = userDao.getAllUsers().size
        assertThat(usersListSize).isLessThan(usersListSizeAfterInsert)
    }

    @Test
    fun readUsersWithBillsTest() {
        val user = User(0, "name", "surname", "email")
        userDao.insertUser(user)
        val userId = userDao.getAllUsers()[0].userId
        val bill = Bill(1, userId, "name", "category", 12.59, (0.0).toLong())
        billDao.insertBill(bill)
        //val userWithBills = UserWithBills(user, listOf(bill))
        assertThat(userDao.getUsersWithBills().size).isAtLeast(1)
        assertThat(userDao.getUsersWithBills()[0].bills[0]).isEqualTo(bill)

    }
}