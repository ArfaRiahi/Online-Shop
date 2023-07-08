package com.example.digikala.ui.fragments.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.digikala.R
import com.example.digikala.databinding.RecyclerItemFirstBinding

class RecyclerHeaderAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<RecyclerHeaderAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: RecyclerItemFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener {
                    onClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            RecyclerItemFirstBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerHeaderAdapter.MyViewHolder, position: Int) {
        holder.apply {
            binding.itemFirstTv.text = "نمایش همه"
            binding.itemFirstNewestIv.setImageResource(R.drawable.gift)
        }
    }
}