package com.odhiambodevelopers.rxkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.BillViewPagerAdapter
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.InfoBillFragment
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.PhotoBillFragment
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.ProductsListBillFragment
import com.odhiambodevelopers.rxkotlin.databinding.ActivityAddBillBinding

class AddBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBillToolbar.title = "Add bill"
        setSupportActionBar(binding.addBillToolbar)

        binding.addBillToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val fragmentList = arrayListOf(
            InfoBillFragment(),
            ProductsListBillFragment(),
            PhotoBillFragment()
        )

        val adapter = BillViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
            )

        binding.billViewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.billViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Info"
                    tab.setIcon(R.drawable.receipt_details)
                }
                1 -> {
                    tab.text = "Products"
                    tab.setIcon(R.drawable.products)
                }
                2 -> {
                    tab.text = "Photo"
                    tab.setIcon(R.drawable.ic_baseline_image_24)
                }
                else -> {
                    tab.text = "Photo"
                    tab.setIcon(R.drawable.ic_baseline_image_24)
                }
            }
        }.attach()

    }
}