package com.odhiambodevelopers.rxkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.odhiambodevelopers.rxkotlin.databinding.ActivityUserDetailsBinding


class UserDetailsActivity : AppCompatActivity() {

    private val TAG = "UserDetailsActivity"
    private lateinit var binding : ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userToolbar.title = "User Details"
        setSupportActionBar(binding.userToolbar)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.user_nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.userToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        //setupActionBarWithNavController(navController, appBarConfiguration)
        //binding.userToolbar.setupWithNavController(navController, appBarConfiguration)

    }
}