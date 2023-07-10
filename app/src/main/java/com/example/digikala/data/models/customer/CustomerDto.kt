package com.example.digikala.data.models.customer

import com.google.gson.annotations.SerializedName

data class CustomerDto(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val id: Int = 0,
)