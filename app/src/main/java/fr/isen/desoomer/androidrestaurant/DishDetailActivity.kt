package fr.isen.desoomer.androidrestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import fr.isen.desoomer.androidrestaurant.adapter.CarouselAdapter
import fr.isen.desoomer.androidrestaurant.base.BaseActivity
import fr.isen.desoomer.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart
import fr.isen.desoomer.androidrestaurant.domain.CartItem
import fr.isen.desoomer.androidrestaurant.domain.Dish
import java.io.File


private lateinit var binding: ActivityDishDetailBinding

class DishDetailActivity : BaseActivity() {
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater);
        setContentView(binding.root);
        val dish = intent.getSerializableExtra("dish") as Dish;
        setTitle(dish.title)
        binding.dishPrice.text = dish.getFormatedPrice();
        for (i in dish.ingredients) {
            binding.dishIngredients.append(i.name);
            binding.dishIngredients.append(", ");
        }
        binding.pager.adapter = CarouselAdapter(this, dish.pictures)
        reduceQuantity()
        increaseQuantity()

        
        binding.totalPrice.setOnClickListener {
            createJsonFile(dish, binding.quantityOrder.text.toString().toInt());
            displayMsg("Added to your cart")
        }
    }

    public fun displayMsg(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    fun updatePriceWithQuantity() {
        var dish = intent.getSerializableExtra("dish") as Dish
        var libeleString = binding.totalPrice.toString();
        var priceString = dish.getPrice() * binding.quantityOrder.text.toString().toDouble()
        var stringToDisplay = "Total Price : " + priceString.toString() + "€"
        binding.totalPrice.setText(stringToDisplay);
    }

    fun reduceQuantity() {
        binding.reduceQuantity.setOnClickListener {
            var quantity = binding.quantityOrder.text.toString().toInt()
            quantity--;
            if (quantity < 0) quantity = 0;
            binding.quantityOrder.setText(quantity.toString())
            updatePriceWithQuantity()
        }
    }

    fun increaseQuantity() {
        binding.addQuantity.setOnClickListener {
            var quantity = binding.quantityOrder.text.toString().toInt()
            quantity++;
            binding.quantityOrder.setText(quantity.toString())
            updatePriceWithQuantity()
        }
    }

    fun createJsonFile(dish: Dish, nbToAdd: Int) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "/$CART_FILE");
        if (file.exists()) {
            val json = gson.fromJson(file.readText(), Cart::class.java)
            json.item.firstOrNull { it.dish == dish }?.let {
                it.quantity = it.quantity?.plus(nbToAdd)
            } ?: run {
                json.item.add(CartItem(dish, nbToAdd))
            }
            saveInMemory(json, file)
        } else {
            val cart = Cart(mutableListOf(CartItem(dish, nbToAdd)))
            saveInMemory(cart, file)
        }
    }

    fun saveInMemory(cart: Cart, file: File) {
        saveDishCount(cart)
        file.writeText(GsonBuilder().setPrettyPrinting().create().toJson(cart))
    }

    fun saveDishCount(cart: Cart) {
        val count = cart.item.sumOf { it.quantity }
        val sharedPreferences = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        sharedPreferences.edit().putInt(CART_COUNT, count).apply()
    }

    companion object {

        const val APP_PREFS = "app_prefs"
        const val CART_FILE = "user_cart.json"
        const val CART_COUNT = "cart_count"
    }
}

