package com.example.digikala.ui.fragments.main

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikala.data.models.products.ProductsResponseItem
import com.example.digikala.databinding.RecyclerItemsBinding

class MainRecyclersAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<ProductsResponseItem, MainRecyclersAdapter.MyViewHolder>(diffUtil) {
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

    inner class MyViewHolder(val binding: RecyclerItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener {
                    onClick(getItem(adapterPosition).id)
                    Log.e(
                        ContentValues.TAG, "absoluteAdapterPosition: ${getItem(adapterPosition).id}"
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclersAdapter.MyViewHolder {
        return MyViewHolder(
            RecyclerItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainRecyclersAdapter.MyViewHolder, position: Int) {
        val item: ProductsResponseItem = getItem(position)
        holder.apply {
            binding.recyclerItemsTvTitle.text = item.name
            Glide.with(binding.root)
                .load(item.images[0].src)
                .into(binding.recyclerItemsIv)
        }
    }
}