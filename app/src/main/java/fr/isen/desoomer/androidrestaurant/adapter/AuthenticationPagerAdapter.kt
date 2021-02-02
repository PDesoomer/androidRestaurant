import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.desoomer.androidrestaurant.LoginFragment
import fr.isen.desoomer.androidrestaurant.RegisterFragment


class AuthenticationPagerAdapter(
    activity: AppCompatActivity,
    fragmentManager: FragmentManager
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LoginFragment() //ChildFragment1 at position 0
            1 -> return RegisterFragment() //ChildFragment2 at position 1
        }
        return LoginFragment() //does not happen
    }
}