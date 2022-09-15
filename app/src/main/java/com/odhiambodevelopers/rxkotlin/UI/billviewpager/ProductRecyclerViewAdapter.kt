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

    var onItemClick : ((ProductWithDebtors) -> Unit)? = null

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

    inner class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {

        val layoutInflater = binding.productExpandableLayout

        fun bind(product: ProductWithDebtors) {
            binding.productNameTv.text = product.product.productName
            binding.productAmountTv.text = product.product.productAmount.toString()
            binding.productPrizeTv.text = "${product.product.productPrize} zł"
            binding.productDebtorsTv.text = product.debtors.size.toString()

            val isVisible: Boolean = product.product.visibility
            binding.productExpandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

            binding.productArrowImv.setOnClickListener {
                product.product.visibility = !product.product.visibility
                //notifyItemChanged(absoluteAdapterPosition)
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }
}