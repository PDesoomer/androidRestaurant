package fr.isen.desoomer.androidrestaurant.domain

import com.google.gson.annotations.SerializedName

data class DishDetailData (@SerializedName(value = "data") var data: List<DishDetail>)