package com.odhiambodevelopers.rxkotlin.database

import androidx.room.*
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.database.models.UserWithBills
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Delete
    fun deleteUser(user: User): Int

    @Query("DELETE FROM User WHERE userId = :userId")
    fun deleteUserWithId(userId: Long)

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM User")
    fun getUsers(): Flowable<List<User>>

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserWithId(userId: Long): User

    @Query("SELECT * FROM User WHERE userEmail = :userEmail")
    fun getUserWithEmail(userEmail: String): User

    @Update
    fun updateUser(user: User): Int

    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithBills(): List<UserWithBills>

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserByUserIdWithBills(userId: Long): List<UserWithBills>
}