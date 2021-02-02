package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
    @SerializedName("data") var user: User
) : Serializable {

}