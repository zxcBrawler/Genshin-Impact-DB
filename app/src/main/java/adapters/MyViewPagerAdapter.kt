package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragments.CharArtefactsFragment
import fragments.CharSkillFragment
import fragments.CharWeaponsFragment
import fragments.DetailsFragment

class MyViewPagerAdapter(fragment: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int {
      return 4
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailsFragment()
            1 -> CharSkillFragment()
            2 -> CharWeaponsFragment()
            3 -> CharArtefactsFragment()
            else -> DetailsFragment()
        }
    }
}