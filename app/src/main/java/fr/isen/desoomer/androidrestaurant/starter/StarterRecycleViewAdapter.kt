package fr.isen.desoomer.androidrestaurant.starter

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
import fr.isen.desoomer.androidrestaurant.domain.Dish
import fr.isen.desoomer.androidrestaurant.databinding.CardTestBinding

class StarterRecycleViewAdapter(private val dataSet: List<Dish>, private val ct: Context) :
    RecyclerView.Adapter<StarterRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(binding: CardTestBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.starterCardTitle2
        val price = binding.starterCardPrice2
        val image = binding.dishPicture
        val container: ConstraintLayout = binding.root;
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding =
            CardTestBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.price.text = dataSet[position].getFormatedPrice().toString()

        Picasso.get()
            .load(dataSet[position].getFirstPicture())
            .transform(BlurTransformation(ct))
            .placeholder(R.drawable.bar_768564_1920)
            .into(holder.image);

        holder.container.setOnClickListener {
            val intent = Intent(ct, DishDetailActivity::class.java)
            intent.putExtra("dish", dataSet[position])
            ct.startActivity(intent);
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size;
    }


}
