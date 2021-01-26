package fr.isen.desoomer.androidrestaurant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.isen.desoomer.androidrestaurant.data.Dish
import fr.isen.desoomer.androidrestaurant.databinding.CardBinding

class StarterRecycleViewAdapter(private val dataSet: List<Dish>, private val ct: Context):
    RecyclerView.Adapter<StarterRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.starterCardTitle
        val price = binding.starterCardPrice
        val image = binding.dishPicture
        val container: ConstraintLayout = binding.root;
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StarterRecycleViewAdapter.ViewHolder {
        val itemBinding = CardBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: StarterRecycleViewAdapter.ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.price.text = dataSet[position].getPrice().toString()
        holder.container.setOnClickListener{
            val intent = Intent(ct, DishDetailActivity::class.java)
            println("Clicked" + position);
            intent.putExtra("dish_product", holder.title.text as String)
            ct.startActivity(intent);
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size;
    }

}