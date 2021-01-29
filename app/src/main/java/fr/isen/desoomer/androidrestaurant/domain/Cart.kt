package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart(
    @SerializedName(value="dish") var dish: List<Dish>,
    @SerializedName(value="quantity") var quantity: Int?
) : Serializable