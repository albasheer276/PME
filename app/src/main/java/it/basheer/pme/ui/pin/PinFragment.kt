package it.basheer.pme.ui.pin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentPinBinding
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.hideKeyboard
import it.basheer.pme.util.showKeyboard

@AndroidEntryPoint
class PinFragment : Fragment() {

    private lateinit var mBinding: FragmentPinBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPinBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.pinOtpView1.requestFocus()
        showKeyboard()

        mBinding.pinOtpView1.doOnTextChanged { text, _, _, _ ->
            if (text.toString().length == 4) {
                mBinding.pinOtpView2.requestFocus()
            }
        }

        val user = BaseApp.getInstance().getUser().value

        mBinding.pinBtnChangePin.setOnClickListener {
            val old = mBinding.pinOtpView1.text.toString()
            val new = mBinding.pinOtpView2.text.toString()

            if (old.toInt() != user?.pin) {
                Toast.makeText(context, getString(R.string.wrong_current_pin), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (new.length != 4) {
                Toast.makeText(context, getString(R.string.pin_4_digits), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            user.pin = new.toInt()
            userViewModel.updateUser(user).observe(viewLifecycleOwner) {
                BaseApp.getInstance().setUser(user)
                mBinding.pinOtpView1.requestFocus()
                hideKeyboard()
                Toast.makeText(context, getString(R.string.pin_changed_success), Toast.LENGTH_SHORT).show()
                mBinding.pinOtpView1.setText("")
                mBinding.pinOtpView2.setText("")
            }
        }
    }
}