package com.example.digikala.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikala.data.models.products.ProductsResponseItem
import com.example.digikala.databinding.RecyclerItemProductsBinding
import com.example.digikala.databinding.RecyclerItemProductsBindingImpl

class RecyclerSearchAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<ProductsResponseItem, RecyclerSearchAdapter.MyViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductsResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ProductsResponseItem,
                newItem: ProductsResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProductsResponseItem,
                newItem: ProductsResponseItem
            ): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }

    inner class MyViewHolder(val binding: RecyclerItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener {
                    onClick(getItem(adapterPosition).id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSearchAdapter.MyViewHolder {

        return MyViewHolder(
            RecyclerItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ProductsResponseItem = getItem(position)
        holder.apply {
            binding.productListTvTitle.text = item.name
            binding.productListTvDes.text =
                HtmlCompat.fromHtml(item.shortDescription.toString(), FROM_HTML_MODE_LEGACY)
            binding.productListTvPrice.text = item.price
            Glide.with(binding.root)
                .load(item.images[0].src)
                .into(binding.productListImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) 1
        else 2
    }
}