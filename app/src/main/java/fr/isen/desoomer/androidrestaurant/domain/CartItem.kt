package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartItem(
    @SerializedName(value="dish") var dish: Dish,
    @SerializedName(value="quantity") var quantity: Int
    ) :Serializable