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

        val cart = menu?.findItem(R.id.action_cart);
        cart?.setActionView(R.layout.cart_item_layout)
        val notifCount = cart?.getActionView()

        val sharedPreferences = getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE)
        var cartQuantity = sharedPreferences.getInt("cart_count", 0)
        val textView = notifCount?.findViewById<TextView>(R.id.cart_badge)
        textView?.setText("" + cartQuantity)

        cart?.setOnMenuItemClickListener{
            val intent = Intent(this, CartDetailActivity::class.java)
            startActivity(intent)
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                Log.d("test", "Text")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}