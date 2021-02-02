package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderData(
    @SerializedName("data") var order: List<Order>
) : Serializable{
}