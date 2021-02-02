package fr.isen.desoomer.androidrestaurant

import SwipeToDeleteCallback
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.SimpleAdapter
import android.widget.Toast
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
import fr.isen.desoomer.androidrestaurant.starter.StarterActivity
import java.io.File


private lateinit var binding: ActivityCartDetailBinding

class CartDetailActivity : AppCompatActivity() {

    fun displayMsg(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailBinding.inflate(layoutInflater);
        binding.cartDetailListView.layoutManager = LinearLayoutManager(this)

        binding.cartValidateButton.setOnClickListener{
            if(getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE).contains("user_id")){
                val intent = Intent(this, OrderActivity::class.java)
                val adapter = binding.cartDetailListView.adapter as CartAdapter
                intent.putExtra("Cart", Cart(adapter.getItem()))
                startActivity(intent);
            } else {
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
            }
        }

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