package it.basheer.pme.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.databinding.FragmentRegisterBinding
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var mBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRegisterBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.submit.setOnClickListener {
            mBinding.progressBar.visibility = View.VISIBLE
            mBinding.submit.isEnabled = false
            mBinding.submit.backgroundTintList = ColorStateList.valueOf(getColor(resources, R.color.md_bluegrey700, requireActivity().theme))

        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                TransitionManager.beginDelayedTransition(mBinding.root, AutoTransition().setDuration(500))
                val layoutParams = mBinding.mainLogo.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.bottomToTop = mBinding.createAccountLayout.id
                mBinding.mainLogo.layoutParams = layoutParams
                mBinding.createAccountLayout.visibility = View.VISIBLE

            }, 300
        )

    }
}