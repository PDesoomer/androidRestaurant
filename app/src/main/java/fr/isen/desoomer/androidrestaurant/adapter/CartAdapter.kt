package fr.isen.desoomer.androidrestaurant.starter

import fr.isen.desoomer.androidrestaurant.transformation.BlurTransformation
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.desoomer.androidrestaurant.DishDetailActivity
import fr.isen.desoomer.androidrestaurant.R
import fr.isen.desoomer.androidrestaurant.databinding.CardBinding
import fr.isen.desoomer.androidrestaurant.domain.Cart
import fr.isen.desoomer.androidrestaurant.domain.CartItem
import java.io.File


class CartAdapter(private val dataSet: MutableList<CartItem>, private val ct: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(binding: CardBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.starterCardTitle
        val image = binding.dishPicture
        val quantity = binding.quantityProduct
        val container: ConstraintLayout = binding.root;
        val totalPrice = binding.totalPricePerItemInBasket;
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding =
            CardBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].dish.title
        holder.quantity.text = "Quantity : " + dataSet[position].quantity.toString();
        holder.totalPrice.text = (dataSet[position].dish.getPrice() * dataSet[position].quantity).toString() + "€"

        Picasso.get()
            .load(dataSet[position].dish.getFirstPicture())
            .transform(BlurTransformation(ct))
            .placeholder(R.drawable.bar_768564_1920)
            .into(holder.image);

        holder.container.setOnClickListener {
            val intent = Intent(ct, DishDetailActivity::class.java)
            intent.putExtra("dish", dataSet[position].dish)
            ct.startActivity(intent);
        }

    }

    override fun getItemCount(): Int {
        return dataSet.size;
    }

    fun getItem(): MutableList<CartItem>{
        return dataSet
    }

    fun removeAt(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}


