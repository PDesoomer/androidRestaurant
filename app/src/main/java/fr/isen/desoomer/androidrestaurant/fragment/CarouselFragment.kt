package fr.isen.desoomer.androidrestaurant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.squareup.picasso.Picasso
import fr.isen.desoomer.androidrestaurant.databinding.ItemBinding

private lateinit var binding: ItemBinding

class CarouselFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString(ARG_OBJECT)?.let {
            Picasso.get()
                .load(it)
                .into(binding.carouselItem);
        }
    }

    companion object {
        const val ARG_OBJECT = "object";
    }

}

