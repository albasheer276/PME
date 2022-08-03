package it.basheer.pme.ui.main_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentMainBinding
import it.basheer.pme.ui.main_activity.MainActivity


class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handle the back pressed action, to close the app, and do not open the splash screen again
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    return
                }
                requireActivity().finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(mBinding.contentMain.fragmentMainToolbar)

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        val navView: NavigationView = mBinding.navView
        val navController = (activity as MainActivity).findNavController(R.id.fragmentMain_containerMain)

        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.rewardFragment, R.id.profileFragment, R.id.membersFragment, R.id.pinFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(activity as MainActivity, navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mBinding.contentMain.fragmentMainToolbar.setNavigationOnClickListener {
            navController.navigateUp(appBarConfiguration)
        }

        BaseApp.getInstance().getUser().observe(viewLifecycleOwner) { user ->
            mBinding.navView.getHeaderView(0).findViewById<TextView>(R.id.navHeader_txtProfileName).text = user?.name
            mBinding.navView.getHeaderView(0).findViewById<TextView>(R.id.navHeader_txtProfilePoints).text = user?.points.toString()
            mBinding.navView.getHeaderView(0).findViewById<TextView>(R.id.navHeader_txtPoints).text = user?.points.toString()
        }
    }

}