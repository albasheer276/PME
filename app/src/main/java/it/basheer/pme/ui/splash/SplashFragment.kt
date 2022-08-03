package it.basheer.pme.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentSplashBinding
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.AppSharedPref
import it.basheer.pme.util.PIN_CODE
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var mBinding: FragmentSplashBinding

    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var mSharedPref: AppSharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSplashBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                userViewModel.getSelectedUser().observe(viewLifecycleOwner) { user ->
                    if (user == null) {
                        findNavController().navigate(R.id.action_splashFragment_to_createProfileFragment)
                        return@observe
                    }
                    BaseApp.getInstance().setUser(user)
                    val pin = mSharedPref.getString(PIN_CODE)
                    if (pin == null) {
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                        return@observe
                    }
                    findNavController().navigate(R.id.action_splashFragment_to_checkPinFragment)
                }
            }, 500
        )
    }
}