package com.odhiambodevelopers.rxkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.odhiambodevelopers.rxkotlin.UI.billviewpager.*
import com.odhiambodevelopers.rxkotlin.databinding.ActivityAddBillBinding

class AddBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            Log.d(TAG, "All permissions granted")
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.addBillToolbar.title = "Add bill"
        setSupportActionBar(binding.addBillToolbar)

        binding.addBillToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

//        val fragmentList = arrayListOf(
//            InfoBillFragment(),
//            ProductsListBillFragment(),
//            PhotoBillFragment()
//        )

        val fragmentList = arrayListOf(
            InfoBillFragment(),
            ProductsListParentFragment(),
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

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(this,
                    "Permissions granted by the user.",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}