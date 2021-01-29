package fr.isen.desoomer.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import fr.isen.desoomer.androidrestaurant.adapter.CarouselAdapter
import fr.isen.desoomer.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.desoomer.androidrestaurant.databinding.ActivityStarterBinding
import fr.isen.desoomer.androidrestaurant.domain.Dish
import fr.isen.desoomer.androidrestaurant.transformation.BlurTransformation

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
            binding.dishIngredients.append(" - ");
        }
        binding.pager.adapter = CarouselAdapter(this, dish.pictures)
        reduceQuantity()
        increaseQuantity()
    }

    fun reduceQuantity(){
        binding.reduceQuantity.setOnClickListener {
            var quantity = binding.quantityOrder.text.toString().toInt()
            quantity--;
            if (quantity < 0) quantity = 0;
            binding.quantityOrder.setText(quantity.toString())
        }
    }

    fun increaseQuantity(){
        binding.addQuantity.setOnClickListener {
            var quantity = binding.quantityOrder.text.toString().toInt()
            quantity++;
            binding.quantityOrder.setText(quantity.toString())
        }
    }
}

