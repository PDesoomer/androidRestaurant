package fr.isen.desoomer.androidrestaurant.starter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.desoomer.androidrestaurant.base.BaseActivity
import fr.isen.desoomer.androidrestaurant.adapter.StarterRecycleViewAdapter
import fr.isen.desoomer.androidrestaurant.databinding.ActivityStarterBinding
import fr.isen.desoomer.androidrestaurant.domain.DishDetailData
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityStarterBinding

class StarterActivity : BaseActivity() {

    var swipeRefreshLayout: SwipeRefreshLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterBinding.inflate(layoutInflater);
        setContentView(binding.root);
        setTitle(intent.getStringExtra("category"));
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        loadData();

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener {
            swipeRefreshLayout!!.isRefreshing = false
            loadData()
            setTitle("Just Refreshed")
        };
    }


    fun loadData() {
        resultFromCache()?.let {
            Log.d("Cache : ", "Loaded from cache")
            displayMsg("Loaded from cache")
            parseResult(it)
        } ?: run {
            Log.d("Cache : ", "Not from cache")
            val postUrl = "http://test.api.catering.bluecodegames.com/menu"
            val requestQueue = Volley.newRequestQueue(this)
            val postData = JSONObject()
            try {
                postData.put("id_shop", "1")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            var request = JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                postData,
                { response ->
                    val gson: DishDetailData =
                        Gson().fromJson(response.toString(), DishDetailData::class.java)
                    gson.data.firstOrNull { it.category == "Entrées" }?.dish?.let {
                        binding.categoryList.adapter = StarterRecycleViewAdapter(it, this);
                    }
                    cacheResult(response.toString())
                },
                { error ->
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                    }
                }
            )
            requestQueue.add(request)
        }
    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE, response)
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }

    private fun parseResult(response: String) {
        val menuResult = GsonBuilder().create().fromJson(response, DishDetailData::class.java)
        menuResult.data.firstOrNull { it.category == "Plats"}?.dish?.let{
            binding.categoryList.adapter = StarterRecycleViewAdapter(it, this);
        }
    }

    fun displayMsg(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    companion object {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }
}
