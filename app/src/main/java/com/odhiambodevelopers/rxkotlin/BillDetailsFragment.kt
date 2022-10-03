package com.odhiambodevelopers.rxkotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.databinding.FragmentBillDetailsBinding
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import com.odhiambodevelopers.rxkotlin.repository.ProductRepository

private const val TAG = "BillDetailsFragment"


class BillDetailsFragment : Fragment() {

    private var _binding: FragmentBillDetailsBinding? = null
    private val binding get() = _binding!!
    private var billId: Long? = null
    private val billViewModel: BillDetailsViewModel by activityViewModels {
        BillDetailsViewModelFactory(
            this,
            BillRepository(AppDatabase.getInstance(requireContext()).billDao),
            ProductRepository(AppDatabase.getInstance(requireContext()).productDao)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            billId = it.getLong(BillDetailsActivity.BILL_ID)
        }
        Log.i(TAG, "billId from fragment: $billId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBillDetailsBinding.inflate(layoutInflater)
        binding.billViewModel = billViewModel
        billViewModel.loadBillInfo(billId!!)
        billViewModel.loadBillProducts(billId!!)
        val adapter = BillDetailsProductRecyclerViewAdapter()
        binding.productsRv.adapter = adapter
        billViewModel.productList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        return binding.root
    }
}