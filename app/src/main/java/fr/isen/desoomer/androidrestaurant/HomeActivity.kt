package fr.isen.desoomer.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    public fun displayMsg(str: String) {
        Toast.makeText(this, "Boutton Cliqué : " + str, Toast.LENGTH_SHORT).show();
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun onClick(v: View) {
        if (v.getId() == R.id.starter_button) {
            val intent = Intent(this, StarterActivity::class.java)
            startActivity(intent);
            displayMsg("Starter Button");
        }
        if (v.getId() == R.id.dish_button) {
            val intent = Intent(this, DishActivity::class.java)
            startActivity(intent);
            displayMsg("Dish Button");
        }

        if (v.getId() == R.id.dessert_button) {
            val intent = Intent(this, DessertActivity::class.java)
            startActivity(intent);
            displayMsg("Dessert Button");
        }

    }
}