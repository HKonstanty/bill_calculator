package com.odhiambodevelopers.rxkotlin.UI.billviewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.odhiambodevelopers.rxkotlin.database.models.Bill
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.databinding.ItemProductBinding

class ProductRecyclerViewAdapter : ListAdapter<ProductWithDebtors, ProductRecyclerViewAdapter.ProductViewHolder>(DiffUtilCallback) {

<<<<<<< HEAD
=======
//    var onItemClick : ((ProductWithDebtors) -> Unit)? = null
>>>>>>> master
    var onItemClick : ((ProductWithDebtors, position: Int) -> Unit)? = null

    object DiffUtilCallback : DiffUtil.ItemCallback<ProductWithDebtors>() {
        override fun areItemsTheSame(
            oldItem: ProductWithDebtors,
            newItem: ProductWithDebtors
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductWithDebtors,
            newItem: ProductWithDebtors
        ): Boolean {
            return oldItem.product.productId == newItem.product.productId
        }
    }

<<<<<<< HEAD
    inner class ProductViewHolder(private val binding: ItemProductBinding,
                                clickAtPosition: (Int) -> (Unit)): RecyclerView.ViewHolder(binding.root) {
=======
    inner class ProductViewHolder(private val binding: ItemProductBinding, clickAtPosition: (Int) -> (Unit)): RecyclerView.ViewHolder(binding.root) {
>>>>>>> master

        val layoutInflater = binding.productExpandableLayout

        init {
            binding.deleteBtn.setOnClickListener { clickAtPosition(absoluteAdapterPosition) }
        }

        fun bind(product: ProductWithDebtors) {
            binding.productNameTv.text = product.product.productName
            binding.productAmountTv.text = product.product.productAmount.toString()
            binding.productPrizeTv.text = "${product.product.productPrize} z??"
            binding.productDebtorsTv.text = product.debtors.size.toString()
            binding.debtorsTv.text = product.debtors.joinToString(", ")

            val isVisible: Boolean = product.product.visibility
            binding.productExpandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                product.product.visibility = !product.product.visibility
                notifyItemChanged(absoluteAdapterPosition)
<<<<<<< HEAD
=======
                //notifyItemChanged(adapterPosition)
>>>>>>> master
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) {
            onItemClick?.invoke(getItem(it), it)
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
<<<<<<< HEAD
=======
//
//        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(product)
//        }
>>>>>>> master
    }
}