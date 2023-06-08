package com.example.digikala.ui.fragments.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.digikala.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapterDetails(private val imageUrl: ArrayList<String>) :
    SliderViewAdapter<SliderAdapterDetails.SliderViewHolder>() {

    override fun getCount(): Int {
        return imageUrl.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterDetails.SliderViewHolder {
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterDetails.SliderViewHolder?, position: Int) {
        if (viewHolder != null) {
            Glide.with(viewHolder.itemView).load(imageUrl[position]).fitCenter()
                .into(viewHolder.imageView)
        }
    }

    class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.sliderImage)
    }
}