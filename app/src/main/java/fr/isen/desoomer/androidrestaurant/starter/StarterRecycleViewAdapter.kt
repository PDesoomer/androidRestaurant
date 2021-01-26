package fr.isen.desoomer.androidrestaurant.starter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
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
    ): ViewHolder {
        val itemBinding = CardBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.price.text = dataSet[position].getFormatedPrice().toString()
        Picasso.get().load(dataSet[position].getFirstPicture()).resize(300, 300).into(holder.image);
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