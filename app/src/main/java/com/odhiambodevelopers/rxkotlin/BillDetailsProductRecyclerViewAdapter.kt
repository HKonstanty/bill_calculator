package com.odhiambodevelopers.rxkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odhiambodevelopers.rxkotlin.database.models.ProductWithDebtors
import com.odhiambodevelopers.rxkotlin.databinding.ItemProductDetailsBinding

class BillDetailsProductRecyclerViewAdapter : RecyclerView.Adapter<BillDetailsProductRecyclerViewAdapter.ProductViewHolder>() {

    private var productList = listOf<ProductWithDebtors>()

    inner class ProductViewHolder(val binding: ItemProductDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productWithDebtors: ProductWithDebtors) {
            binding.product = productWithDebtors.product
            binding.productDebtorsTv.text = productWithDebtors.debtors.size.toString()
            binding.productPrizeTv.text = productWithDebtors.product.productPrize.toString() + "zł"
        }
    }

    fun setData(list: List<ProductWithDebtors>) {
        productList = list
        notifyItemRangeInserted(0, list.size)
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(ItemProductDetailsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
        holder.itemView.context.getString(R.string.prize_placeholder)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}