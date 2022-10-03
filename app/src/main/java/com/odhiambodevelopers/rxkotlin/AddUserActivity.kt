package com.odhiambodevelopers.rxkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.database.UserDao
import com.odhiambodevelopers.rxkotlin.database.models.User
import com.odhiambodevelopers.rxkotlin.databinding.ActivityAddUserBinding
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//import io.reactivex.rxjava3.core.Flowable
//import io.reactivex.rxjava3.core.Maybe
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//import io.reactivex.rxjava3.schedulers.Schedulers

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var detailsDatabase: AppDatabase
    private lateinit var userDao: UserDao
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addUserToolbar.title = "Add user"
        setSupportActionBar(binding.addUserToolbar)

        binding.addUserToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.saveUserBt.setOnClickListener {
            if (binding.userNameEt.text.toString().isEmpty()) {
                binding.userNameEt.error = "Required"
                return@setOnClickListener
            }
            else if (binding.userSurnameEt.text.toString().isEmpty()) {
                binding.userSurnameEt.error = "Required"
                return@setOnClickListener
            }
            else if (binding.userEmailEt.text.toString().isEmpty()) {
                binding.userEmailEt.error = "Required"
                return@setOnClickListener
            }
            else {
                val loadDisposable = addingUser(this).subscribe()
                compositeDisposable.add(loadDisposable)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun addingUser(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>(){
            val userDao = AppDatabase.getInstance(context).userDao

            val user = User(0,
                binding.userNameEt.text.toString(),
                binding.userSurnameEt.text.toString(),
                binding.userEmailEt.text.toString())

            userDao.insertUser(user)
        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
            }
            .doOnError {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
            }
    }
}