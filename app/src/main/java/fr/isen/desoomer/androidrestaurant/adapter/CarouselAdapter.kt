package fr.isen.desoomer.androidrestaurant.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.desoomer.androidrestaurant.fragment.CarouselFragment

class CarouselAdapter(
    activity: AppCompatActivity,
    val list: List<String>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = CarouselFragment()
        fragment.arguments = Bundle().apply {
            putString(CarouselFragment.ARG_OBJECT, list[position])
        }
        return fragment
    }
}