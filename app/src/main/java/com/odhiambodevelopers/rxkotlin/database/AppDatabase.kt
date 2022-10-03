package com.odhiambodevelopers.rxkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.odhiambodevelopers.rxkotlin.database.models.*

@Database(entities = [User::class, Bill::class, Product::class, ProductDebtorCrossRef::class, BillDebtorCrossRef::class], exportSchema = false, version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract val userDao: UserDao
    abstract val billDao: BillDao
    abstract val productDao: ProductDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "notes_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}