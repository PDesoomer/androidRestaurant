package fr.isen.desoomer.androidrestaurant

import AuthenticationPagerAdapter
import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import fr.isen.desoomer.androidrestaurant.base.BaseActivity
import fr.isen.desoomer.androidrestaurant.databinding.ActivityAuthenticationBinding
import fr.isen.desoomer.androidrestaurant.databinding.ActivityDishDetailBinding

private lateinit var binding: ActivityAuthenticationBinding
class AuthenticationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.viewPager.adapter = AuthenticationPagerAdapter(this, supportFragmentManager)
    }


}