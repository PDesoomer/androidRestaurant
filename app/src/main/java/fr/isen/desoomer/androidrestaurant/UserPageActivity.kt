package fr.isen.desoomer.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.desoomer.androidrestaurant.databinding.ActivityUserPageBinding

private lateinit var binding: ActivityUserPageBinding

class UserPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener{
            val sharedPreferences = getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE)
            sharedPreferences.edit().remove("user_id").commit();
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}