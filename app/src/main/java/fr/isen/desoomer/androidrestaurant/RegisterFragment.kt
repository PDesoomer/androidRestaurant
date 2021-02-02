package fr.isen.desoomer.androidrestaurant

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.afollestad.vvalidator.form
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.adapter.StarterRecycleViewAdapter
import fr.isen.desoomer.androidrestaurant.databinding.FragmentRegisterBinding
import fr.isen.desoomer.androidrestaurant.databinding.ItemBinding
import fr.isen.desoomer.androidrestaurant.domain.DishDetailData
import fr.isen.desoomer.androidrestaurant.domain.User
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding : FragmentRegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        form {
            input(binding.etName){
                isNotEmpty()
                length().atLeast(3)
            }
            input(binding.etEmail){
                isNotEmpty()
                length().atLeast(6)
                matches("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
            }
            input(binding.etPassword){
                isNotEmpty()
                length().atLeast(8)
            }
            input(binding.etAdresse){
                isNotEmpty()
                length().atLeast(5)
            }

            submitWith(binding.btnRegister) { result ->
                sendUserToAPI()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun sendUserToAPI() {
        val postUrl = "http://test.api.catering.bluecodegames.com/user/register"
        val requestQueue = Volley.newRequestQueue(activity?.applicationContext)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
            postData.put("firstname", binding.etName.text)
            postData.put("lastname", binding.etLastname.text)
            postData.put("address", binding.etAdresse.text)
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
                val gson: User =
                    Gson().fromJson(response.toString(), User::class.java)
                println(response)
            },
            { error ->
                error.printStackTrace()
            })
        requestQueue.add(jsonObjectRequest)
    }
}