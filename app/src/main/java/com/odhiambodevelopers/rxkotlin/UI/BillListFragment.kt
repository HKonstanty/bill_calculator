package com.odhiambodevelopers.rxkotlin.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.odhiambodevelopers.rxkotlin.BillDetailsActivity
import com.odhiambodevelopers.rxkotlin.R
import com.odhiambodevelopers.rxkotlin.UI.placeholder.PlaceholderContent
import com.odhiambodevelopers.rxkotlin.database.AppDatabase
import com.odhiambodevelopers.rxkotlin.databinding.FragmentBillListBinding
import com.odhiambodevelopers.rxkotlin.repository.BillRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class BillListFragment : Fragment() {

    private val TAG = "BillListFragment"

    private var _binding: FragmentBillListBinding? = null
    private val binding get() = _binding!!
    private val disposable = CompositeDisposable()
    private val adapter by lazy { BillRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBillListBinding.inflate(layoutInflater)

        val display = dataDisplay(requireContext()).subscribe()
        disposable.add(display)

        adapter.onItemClick = { it ->
            Log.d(TAG, "Item clicked ${it.billName}")
            Intent(context, BillDetailsActivity::class.java).apply {
                putExtra(BILL_ID, it.billId)
            }.also {
                startActivity(it)
            }
        }

        return binding.root
    }

    private fun dataDisplay(requireContext: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>() {
            val billDao = AppDatabase.getInstance(requireContext).billDao
            val billList = billDao.getBills()
            adapter.submitList(billList)
            binding.billListRv.adapter = adapter
        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
            }
            .doOnError{
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
            }
            .doOnNext {
                Log.d(TAG, "Added new bill")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BILL_ID = "bill_id"
        const val BILL = "bill"
    }
}