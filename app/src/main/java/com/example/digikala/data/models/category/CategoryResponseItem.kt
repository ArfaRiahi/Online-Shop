package com.example.digikala.data.models.category

import com.google.gson.annotations.SerializedName

data class CategoryResponseItem(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("display")
    val display: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("menu_order")
    val menuOrder: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent")
    val parent: Int?,
    @SerializedName("slug")
    val slug: String?
)