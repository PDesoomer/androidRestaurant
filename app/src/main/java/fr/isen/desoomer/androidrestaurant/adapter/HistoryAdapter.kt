package fr.isen.desoomer.androidrestaurant.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
import fr.isen.desoomer.androidrestaurant.databinding.HistoryCardBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart
import fr.isen.desoomer.androidrestaurant.domain.Order
import fr.isen.desoomer.androidrestaurant.domain.OrderData

class HistoryAdapter(private val dataSet: List<Order>, private val ct: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: HistoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val container: ConstraintLayout = binding.root;
        val title = binding.numberOfDishOrdered
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            HistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var text :String = ""
        val orderCart = Gson().fromJson(dataSet[position].message, Cart::class.java) as Cart
        for(c in orderCart.item){
            text += " | " + c.dish.title + " x " + c.quantity + " | "
        }
        holder.title.text = text
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
