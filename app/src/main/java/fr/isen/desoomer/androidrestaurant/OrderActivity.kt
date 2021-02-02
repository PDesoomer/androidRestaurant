package fr.isen.desoomer.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.domain.UserData
import org.json.JSONException
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        sendOrderToAPI()
        var cart_icon = findViewById<LottieAnimationView>(R.id.waiting_for_delivery)
        cart_icon.playAnimation()
    }

    fun sendOrderToAPI() {
        val postUrl = "http://test.api.catering.bluecodegames.com/user/order"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("id_user", getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE).getInt("user_id", 0))
            postData.put("msg", intent.getSerializableExtra("Cart"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response ->
                Log.i("Reponse : ", ""+response)
                var cart_icon = findViewById<LottieAnimationView>(R.id.waiting_for_delivery)
                cart_icon.visibility = View.GONE
            },
            { error ->
                error.printStackTrace()
            })
        requestQueue.add(jsonObjectRequest)
    }
}