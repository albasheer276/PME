package it.basheer.pme.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentSplashBinding
import it.basheer.pme.ui.view_models.UserViewModel

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var mBinding: FragmentSplashBinding

    private val userViewModel: UserViewModel by viewModels()

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
                TransitionManager.beginDelayedTransition(mBinding.root, AutoTransition().setDuration(500))
                val layoutParams = mBinding.splashImgIcon.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.bottomToTop = mBinding.splashGuide.id
                layoutParams.topToTop = ConstraintLayout.LayoutParams.UNSET
                mBinding.splashImgIcon.layoutParams = layoutParams
                mBinding.splashLblTitle.visibility = View.VISIBLE

            }, 250
        )

        Handler(Looper.getMainLooper()).postDelayed(
            {
                userViewModel.getParentUser().observe(viewLifecycleOwner) { user ->
                    //if (user == null){
                        findNavController().navigate(R.id.action_splashFragment_to_createProfileFragment)
                        return@observe
                   //B }
                    BaseApp.getInstance().user = user
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }

            }, 1000
        )
    }
}