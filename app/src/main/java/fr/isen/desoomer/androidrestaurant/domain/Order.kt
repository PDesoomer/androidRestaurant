package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Order(
    @SerializedName("message") var message: String
):Serializable{
    fun getCart() = Gson().fromJson(message, Cart::class.java)
}