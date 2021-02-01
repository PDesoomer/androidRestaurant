package fr.isen.desoomer.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.desoomer.androidrestaurant.databinding.ActivityCartDetailBinding
import fr.isen.desoomer.androidrestaurant.databinding.ActivityStarterBinding

private lateinit var binding : ActivityCartDetailBinding

class CartDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailBinding.inflate(layoutInflater);
        setContentView(binding.root);

        //binding.cartDetailListView.adapter = CartAdapter();
    }
}