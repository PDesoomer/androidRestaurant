package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredients (@SerializedName(value = "name_fr") val name: String) : Serializable
