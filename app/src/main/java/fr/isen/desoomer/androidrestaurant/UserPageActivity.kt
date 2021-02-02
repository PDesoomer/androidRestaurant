package fr.isen.desoomer.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.adapter.HistoryAdapter
import fr.isen.desoomer.androidrestaurant.adapter.StarterRecycleViewAdapter
import fr.isen.desoomer.androidrestaurant.databinding.ActivityUserPageBinding
import fr.isen.desoomer.androidrestaurant.domain.OrderData
import fr.isen.desoomer.androidrestaurant.domain.UserData
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: ActivityUserPageBinding

class UserPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)

        val sharedPreferences = getSharedPreferences(DishDetailActivity.APP_PREFS, MODE_PRIVATE)
        if (sharedPreferences.contains("user_id")) {
            val user_id = sharedPreferences.getInt("user_id", 0)
            checkPreviousOrders(user_id)
        }

        binding.logout.setOnClickListener {
            sharedPreferences.edit().remove("user_id").commit();
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }

    fun checkPreviousOrders(user_id: Int) {
        val postUrl = "http://test.api.catering.bluecodegames.com/listorders"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("id_user", user_id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response ->
                val gson: OrderData = Gson().fromJson(response.toString(), OrderData::class.java)
                Log.i("Retour", "" + gson.order[0].getCart())
                gson.order?.let {
                    binding.recyclerHistory.adapter = HistoryAdapter(it, this)
                }
            },
            { error ->
                error.printStackTrace()
            })

        requestQueue.add(jsonObjectRequest)
    }
}