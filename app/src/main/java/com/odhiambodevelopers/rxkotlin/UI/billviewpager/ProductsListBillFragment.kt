package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.databinding.FragmentProductsListBillBinding
import com.odhiambodevelopers.rxkotlin.repository.FakeBillRepository


class ProductsListBillFragment : Fragment() {

    private var _binding: FragmentProductsListBillBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { ProductRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsListBillBinding.inflate(layoutInflater)

        val fakeRepo = FakeBillRepository()
        adapter.submitList(fakeRepo.getListProductsWithDebtora())
        binding.billProductsRv.adapter = adapter


        adapter.onItemClick = {
            Log.d(TAG, "Item clicked ${it.product.productName}")
        }
        binding.addProductBt.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.products_list_fragment, AddProductFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "ProductsBillFragment"
    }
}