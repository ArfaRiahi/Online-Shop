package com.example.digikala.ui.fragments.category

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digikala.data.models.category.CategoryResponseItem
import com.example.digikala.databinding.RecyclerItemCategoryBinding


class CategoryFragmentRecyclerAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<CategoryResponseItem, CategoryFragmentRecyclerAdapter.MyViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CategoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: CategoryResponseItem,
                newItem: CategoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CategoryResponseItem,
                newItem: CategoryResponseItem
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    inner class MyViewHolder(val binding: RecyclerItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener {
                    onClick(getItem(adapterPosition).id)
                    Log.e(
                        ContentValues.TAG,
                        "absoluteAdapterPosition: ${getItem(adapterPosition).id}"
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryFragmentRecyclerAdapter.MyViewHolder {
        return MyViewHolder(
            RecyclerItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: CategoryResponseItem = getItem(position)
        holder.apply {
            Log.e("TAG", "onBindViewHolder: " + item.name.toString())
            binding.titleCategory.text = item.name
            Glide.with(binding.root)
                .load(item.image.src)
                .into(binding.imageCategory)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 1) 1
        else 2
    }
}