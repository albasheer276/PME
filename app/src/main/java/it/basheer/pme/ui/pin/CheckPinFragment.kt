package it.basheer.pme.ui.pin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentCheckPinBinding
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.showKeyboard

class CheckPinFragment : Fragment() {

    private lateinit var mBinding: FragmentCheckPinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCheckPinBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.checkPinOtpView.requestFocus()
        showKeyboard()

        mBinding.checkPinOtpView.doOnTextChanged { text, _, _, count ->
            if (text.toString().length == 4) {
                val user = BaseApp.getInstance().user ?: return@doOnTextChanged
                if (user.pin == text.toString().toInt()) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        mBinding.checkPinTxtError.visibility = View.INVISIBLE
                        findNavController().navigate(R.id.action_checkPinFragment_to_mainFragment)
                    }, 500)
                } else {
                    mBinding.checkPinTxtError.visibility = View.VISIBLE
                }
            } else {
                mBinding.checkPinTxtError.visibility = View.INVISIBLE
            }
        }
    }
}