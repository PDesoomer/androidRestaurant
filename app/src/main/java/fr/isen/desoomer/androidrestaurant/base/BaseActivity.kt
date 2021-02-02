package fr.isen.desoomer.androidrestaurant.base

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.desoomer.androidrestaurant.CartDetailActivity
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
import fr.isen.desoomer.androidrestaurant.R
import fr.isen.desoomer.androidrestaurant.UserPageActivity


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

        if(sharedPreferences.contains("user_id")){
            val userButton = menu?.findItem(R.id.user_icon)
            userButton?.isVisible = true
            val user = menu?.findItem(R.id.user_icon)?.actionView
            user?.setOnClickListener{
                val intent = Intent(this, UserPageActivity::class.java)
                startActivity(intent)
            }
        }


        return super.onCreateOptionsMenu(menu)
        invalidateOptionsMenu()
    }




}