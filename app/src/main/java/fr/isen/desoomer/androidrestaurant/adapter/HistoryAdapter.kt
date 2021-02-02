package fr.isen.desoomer.androidrestaurant.adapter

import fr.isen.desoomer.androidrestaurant.transformation.BlurTransformation
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
import fr.isen.desoomer.androidrestaurant.R
import fr.isen.desoomer.androidrestaurant.databinding.CardBinding
import fr.isen.desoomer.androidrestaurant.domain.Dish
import fr.isen.desoomer.androidrestaurant.databinding.CardTestBinding
import fr.isen.desoomer.androidrestaurant.databinding.HistoryCardBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart

class HistoryAdapter(private val dataSet: Cart, private val ct: Context) :
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
        holder.title.text = dataSet.item.size.toString()
        println("Value : " + dataSet.item.size.toString())
        holder.container.setOnClickListener {
            val intent = Intent(ct, DishDetailActivity::class.java)
            intent.putExtra("order", dataSet.item[position])
            ct.startActivity(intent);
        }
    }

    override fun getItemCount(): Int {
        return dataSet.item.size
    }
}
