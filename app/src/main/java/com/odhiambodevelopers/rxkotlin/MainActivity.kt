package com.odhiambodevelopers.rxkotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.odhiambodevelopers.rxkotlin.UI.ModalBottomSheetFragment
import com.odhiambodevelopers.rxkotlin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var detailsDatabase: AppDatabase
//    private lateinit var detailsDao: UserDao
//    private val adapter by lazy { DetailsAdapter() }
    //    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation = binding.bottomNavigation

        setSupportActionBar(binding.mainToolbar)
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.userListFragment, R.id.billListFragment, R.id.settlementListFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        //binding.mainToolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNavigation.setupWithNavController(navController)

        binding.btnFab.setOnClickListener {
            val modalBottomSheet = ModalBottomSheetFragment()
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheetFragment.TAG)
        }
    }
}