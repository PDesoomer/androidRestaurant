package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName(value = "firstname") var firstname: String,
    @SerializedName(value="id") var id: Int,
    @SerializedName(value = "lastname") var lastname: String,
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "adresse") var adresse: String,
    @SerializedName(value = "password") var password: String,
) : Serializable {
}