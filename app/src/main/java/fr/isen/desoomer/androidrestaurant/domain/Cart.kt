package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart(
    @SerializedName(value="dishes") var item: MutableList<CartItem>
) : Serializable