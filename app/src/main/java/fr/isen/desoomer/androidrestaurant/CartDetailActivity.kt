package fr.isen.desoomer.androidrestaurant

import SwipeToDeleteCallback
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.desoomer.androidrestaurant.databinding.ActivityCartDetailBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart
import fr.isen.desoomer.androidrestaurant.domain.CartItem
import fr.isen.desoomer.androidrestaurant.domain.Dish
import fr.isen.desoomer.androidrestaurant.starter.CartAdapter
import java.io.File


private lateinit var binding: ActivityCartDetailBinding

class CartDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailBinding.inflate(layoutInflater);
        binding.cartDetailListView.layoutManager = LinearLayoutManager(this)



        setContentView(binding.root);
        val file = File(cacheDir.absolutePath + "/${DishDetailActivity.CART_FILE}");
        if (file.exists()) {
            val json = Gson().fromJson(file.readText(), Cart::class.java)
            binding.cartDetailListView.adapter = CartAdapter(json.item, this)
        }


        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.cartDetailListView.adapter as CartAdapter
                adapter.removeAt(viewHolder.adapterPosition)

                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(Cart(adapter.getItem()))

                val file = File(cacheDir.absolutePath + "/${DishDetailActivity.CART_FILE}");
                if (file.exists()) {
                    file.writeText(json)
                    saveDishCount(Cart(adapter.getItem()))
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.cartDetailListView)
    }

    fun saveDishCount(cart: Cart) {
        val count = cart.item.sumOf { it.quantity }
        val sharedPreferences = getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().putInt(DishDetailActivity.CART_COUNT, count).apply()
        invalidateOptionsMenu()
    }

}