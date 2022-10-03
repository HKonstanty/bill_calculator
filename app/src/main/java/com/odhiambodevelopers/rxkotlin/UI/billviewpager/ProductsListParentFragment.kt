package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.databinding.FragmentProductsListParentBinding

const val TAG = "BACKSTACK"

class ProductsListParentFragment : Fragment() {

    private var _binding: FragmentProductsListParentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsListParentBinding.inflate(layoutInflater)

        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<ProductsListBillFragment>(R.id.fragment_container_view)
        }

        return binding.root
    }
}