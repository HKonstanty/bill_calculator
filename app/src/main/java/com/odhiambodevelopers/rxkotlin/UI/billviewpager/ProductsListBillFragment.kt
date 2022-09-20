package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.databinding.FragmentProductsListBillBinding
import com.odhiambodevelopers.rxkotlin.repository.FakeBillRepository


class ProductsListBillFragment : Fragment() {

    private var _binding: FragmentProductsListBillBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { ProductRecyclerViewAdapter() }
    private val viewModel: BillViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsListBillBinding.inflate(layoutInflater)

        val fakeRepo = FakeBillRepository()
        //adapter.submitList(fakeRepo.getListProductsWithDebtora())
        adapter.submitList(viewModel.productList.value)
        binding.billProductsRv.adapter = adapter

        viewModel.productList.observe(requireActivity(), Observer { it ->
            adapter.notifyItemInserted(it.size)
            Log.i(TAG, "Product list changed")
        })


//        adapter.onItemClick = {
//            Log.d(TAG, "Item clicked ${it.product.productName}")
//        }
        adapter.onItemClick = { productWithDebtors: ProductWithDebtors, i: Int ->
            Log.d(TAG, "Item clicked ${productWithDebtors.product.productName}")
            viewModel.deleteProduct(productWithDebtors)
            adapter.notifyItemRemoved(i)
        }

            binding.addProductBt.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddProductFragment>(R.id.fragment_container_view)
                addToBackStack("add_product")
            }
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