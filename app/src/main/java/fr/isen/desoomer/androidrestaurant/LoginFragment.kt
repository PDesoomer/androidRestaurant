package fr.isen.desoomer.androidrestaurant

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.databinding.FragmentLoginBinding
import fr.isen.desoomer.androidrestaurant.databinding.FragmentRegisterBinding
import fr.isen.desoomer.androidrestaurant.domain.User
import fr.isen.desoomer.androidrestaurant.domain.UserData
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: FragmentLoginBinding

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        form {
            input(binding.etPassword) {
                isNotEmpty()
                length().atLeast(8)
            }
            input(binding.etEmail) {
                isNotEmpty()
                length().atLeast(6)
                matches("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
            }


            submitWith(binding.btnLogin) { result ->
                checkIfUserLoggedIn()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun displayMsg(str: String) {
        Toast.makeText(activity?.applicationContext, str, Toast.LENGTH_SHORT).show();
    }

    fun checkIfUserLoggedIn() {
        val postUrl = "http://test.api.catering.bluecodegames.com/user/login"
        val requestQueue = Volley.newRequestQueue(activity?.applicationContext)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("email", binding.etEmail.text)
            postData.put("password", binding.etPassword.text)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            postUrl,
            postData,
            { response ->
                val gson: UserData = Gson().fromJson(response.toString(), UserData::class.java)
                Log.i("Registered ? ", "" + gson.user)
                if (gson.user == null) {
                    displayMsg("User not registed, please register now")
                } else {
                    val sharedPreferences = this.activity?.getSharedPreferences(DishDetailActivity.APP_PREFS, AppCompatActivity.MODE_PRIVATE)
                    sharedPreferences?.edit()?.putInt("user_id", gson.user.id)?.apply()
                    displayMsg("successfully logged in ! ")
                }
            },
            { error ->
                error.printStackTrace()
            })
        requestQueue.add(jsonObjectRequest)
    }
}