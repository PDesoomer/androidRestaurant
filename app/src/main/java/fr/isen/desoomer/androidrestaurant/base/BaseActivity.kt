package fr.isen.desoomer.androidrestaurant.base

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.desoomer.androidrestaurant.CartDetailActivity
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
import fr.isen.desoomer.androidrestaurant.R


open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_chart, menu)

        val cart = menu?.findItem(R.id.action_cart)?.actionView

        val sharedPreferences = getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE)
        val cartQuantity = sharedPreferences.getInt("cart_count", 0)
        cart?.findViewById<TextView>(R.id.cart_badge)?.text = cartQuantity.toString()

        cart?.setOnClickListener{
            val intent = Intent(this, CartDetailActivity::class.java)
            startActivity(intent)
        }

        return super.onCreateOptionsMenu(menu)
        invalidateOptionsMenu()
    }

}