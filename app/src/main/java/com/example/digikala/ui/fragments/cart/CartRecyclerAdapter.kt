package com.example.digikala.ui.fragments.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikala.data.models.products.ProductsResponseItem
import com.example.digikala.databinding.CartRecyclerItemBinding

class CartRecyclerAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<ProductsResponseItem, CartRecyclerAdapter.MyViewHolder>(diffUtil) {

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

    inner class MyViewHolder(val binding: CartRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener {
                    onClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartRecyclerAdapter.MyViewHolder {
        return MyViewHolder(
            CartRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ProductsResponseItem = getItem(position)
        holder.apply {
            binding.recyclerCartPrice.text = item.price
            binding.recyclerCartTvTitle.text = item.name
            Glide.with(binding.root)
                .load(item.images[0].src)
                .into(binding.recyclerCartIv)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) 1
        else 2
    }
}