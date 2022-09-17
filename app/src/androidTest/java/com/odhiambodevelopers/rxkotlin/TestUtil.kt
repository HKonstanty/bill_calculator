package com.odhiambodevelopers.rxkotlin

import com.odhiambodevelopers.rxkotlin.database.models.User

object TestUtil {

    fun createUser(): User {
        return User(0, "name", "surname", "email")
    }
}