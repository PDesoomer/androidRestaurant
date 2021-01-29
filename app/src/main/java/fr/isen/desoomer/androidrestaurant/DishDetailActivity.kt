package fr.isen.desoomer.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.GsonBuilder
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
            createJsonFile(listOf(dish), binding.quantityOrder.text.toString().toInt());
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

    fun createJsonFile(dish: List<Dish>, nbToAdd: Int) {
        val cart = Cart(
            dish,
            binding.quantityOrder.text.toString().toInt()
        )
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(cacheDir.absolutePath + "fzefezhf.json");
        if (file.exists()) {
           val json = gson.fromJson(file.readText(), Cart::class.java)
            json.quantity = json.quantity?.plus(nbToAdd);
            json.dish = dish
            file.writeText(gson.toJson(json));
        } else {
            val jsonObject = gson.toJson(Cart(dish, nbToAdd))
            file.writeText(jsonObject);
        }
    }
}

