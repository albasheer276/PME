package it.basheer.pme.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import it.basheer.pme.ui.home.NegativeTasksFragment
import it.basheer.pme.ui.home.PositiveTasksFragment

class VPAdapter constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PositiveTasksFragment()
            else -> NegativeTasksFragment()
        }
    }

}