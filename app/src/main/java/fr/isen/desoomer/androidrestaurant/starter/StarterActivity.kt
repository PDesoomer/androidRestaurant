package fr.isen.desoomer.androidrestaurant.starter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.data.DishDetailData
import fr.isen.desoomer.androidrestaurant.databinding.ActivityStarterBinding
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityStarterBinding

class StarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.starterTitle.text = intent.getStringExtra("category");

        binding.categoryList.layoutManager = LinearLayoutManager(this)

        loadDataFromApi();
    }

    fun loadDataFromApi() {
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response ->
                val gson: DishDetailData =
                    Gson().fromJson(response.toString(), DishDetailData::class.java)
                gson.data.firstOrNull { it.category == "Entrées" }?.dish?.let {
                    binding.categoryList.adapter = StarterRecycleViewAdapter(it, this);
                }
            },
            { error ->
                error.printStackTrace()
            })
        requestQueue.add(jsonObjectRequest)
    }
}
