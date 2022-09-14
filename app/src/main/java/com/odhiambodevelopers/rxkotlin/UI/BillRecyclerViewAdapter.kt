package com.odhiambodevelopers.rxkotlin.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.databinding.ItemBillBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class BillRecyclerViewAdapter
    : ListAdapter<Bill, BillRecyclerViewAdapter.BillViewHolder>(DiffUtilCallback) {

    var onItemClick : ((Bill) -> Unit)? = null

    object DiffUtilCallback : DiffUtil.ItemCallback<Bill>() {
        override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.billId == newItem.billId
        }
    }

    inner class BillViewHolder(private val binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bill: Bill) {
            binding.billTitleTv.text = bill.billName
            //binding.billPrizeTv.text = bill.prize.toString()
            binding.billDebtorsNumberTv.text = "1"
            binding.billPrizeTv.text = "${bill.prize} zł"
            val date = Date(bill.date)
            //val formatter = SimpleDateFormat("dd-MMMM-yyyy", Locale.GERMAN)
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN)
            val formattedDate = formatter.format(date)
            binding.billItemDateTv.text = formattedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        return BillViewHolder(ItemBillBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        val bill = getItem(position)
        holder.bind(bill)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(bill)
        }
    }
}