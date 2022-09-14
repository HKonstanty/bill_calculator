package com.odhiambodevelopers.rxkotlin.repository

import com.odhiambodevelopers.rxkotlin.database.UserDao
import com.odhiambodevelopers.rxkotlin.database.models.User

class UserRepository(private val userDao: UserDao) {

    fun getUsers() = userDao.getAllUsers()

    fun getUsersFlowable() = userDao.getUsers()

    suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User): Int {
        return userDao.deleteUser(user)
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun getUserWithId(userId: Long): User {
        return userDao.getUserWithId(userId)
    }
}