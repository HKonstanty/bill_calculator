package com.odhiambodevelopers.rxkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.odhiambodevelopers.rxkotlin.UI.BillListFragment
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.BillViewModel
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.BillViewModelFactory
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.databinding.ActivityBillDetailsBinding
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import com.odhiambodevelopers.rxkotlin.repository.ProductRepository
import com.odhiambodevelopers.rxkotlin.repository.UserRepository

class BillDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityBillDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: BillDetailsViewModel by viewModels {
        BillDetailsViewModelFactory(
            this,
            BillRepository(AppDatabase.getInstance(this).billDao),
            ProductRepository(AppDatabase.getInstance(this).productDao)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBillDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val billId = intent.getLongExtra(BillListFragment.BILL_ID, 0)
        Log.i(TAG, "BIll id $billId")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.bill_details_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val bundle: Bundle = Bundle()
        bundle.putLong(BILL_ID, billId)
        navController.setGraph(R.navigation.bill_details_nav_graph, bundle)
        binding.materialToolbar.title = "Bill details"
        setSupportActionBar(binding.materialToolbar)
        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        //val appBarConfig = AppBarConfiguration(navController.graph)
        //val appBarConfig = AppBarConfiguration(setOf(R.id.billDetailsFragment))
        //setupActionBarWithNavController(navController, appBarConfig)
        //binding.materialToolbar.setupWithNavController(navController, appBarConfig)
        //binding.materialToolbar.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BILL_ID = "BillId"
        private const val TAG = "BillDetailsActivity"
    }
}