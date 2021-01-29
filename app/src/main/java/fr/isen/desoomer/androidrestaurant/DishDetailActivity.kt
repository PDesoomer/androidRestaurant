package fr.isen.desoomer.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.adapter.CarouselAdapter
import fr.isen.desoomer.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart
import fr.isen.desoomer.androidrestaurant.domain.Dish
import java.io.File
import java.io.FileWriter

private lateinit var binding: ActivityDishDetailBinding

class DishDetailActivity : AppCompatActivity() {
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
            createJsonFile();
        }
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

    fun createJsonFile() {
        var dish = intent.getSerializableExtra("dish") as Dish
        val cart = Cart(
            dish.title,
            binding.quantityOrder.text.toString().toInt(),
            dish.getPrice() * binding.quantityOrder.text.toString().toInt()
        )
        val json = Gson().toJson(cart)
        val file = File(cacheDir.absolutePath + "Cart.json");
        file.writeText(json);
    }
}

