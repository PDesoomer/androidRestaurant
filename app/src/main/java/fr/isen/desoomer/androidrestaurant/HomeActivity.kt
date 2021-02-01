package fr.isen.desoomer.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import fr.isen.desoomer.androidrestaurant.base.BaseActivity
import fr.isen.desoomer.androidrestaurant.databinding.ActivityHomeBinding
import fr.isen.desoomer.androidrestaurant.starter.DessertActivity
import fr.isen.desoomer.androidrestaurant.starter.DishActivity
import fr.isen.desoomer.androidrestaurant.starter.StarterActivity

private lateinit var binding: ActivityHomeBinding;

class HomeActivity : BaseActivity(){
    public fun displayMsg(str: String) {
        Toast.makeText(this, "Clicked : " + str, Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.starterButton.setOnClickListener {
            val intent = Intent(this, StarterActivity::class.java)
            intent.putExtra("category", "Starter")
            startActivity(intent);
            displayMsg("Starter Button");
        }

        binding.dishButton.setOnClickListener {
            val intent = Intent(this, DishActivity::class.java)
            intent.putExtra("category", "Dish")
            startActivity(intent);
            displayMsg("Dish Button");
        }

        binding.dessertButton.setOnClickListener{
            val intent = Intent(this, DessertActivity::class.java)
            intent.putExtra("category", "Dessert")
            startActivity(intent);
            displayMsg("Dessert Button");
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Home Activity Destroyed");
    }
}