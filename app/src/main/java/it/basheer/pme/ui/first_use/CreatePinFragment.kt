package it.basheer.pme.ui.first_use

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentCreatePinBinding
import it.basheer.pme.ui.view_models.UserViewModel

@AndroidEntryPoint
class CreatePinFragment : Fragment() {

    private lateinit var mBinding: FragmentCreatePinBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCreatePinBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.createPinOtpView.requestFocus()

        mBinding.createPinBtnSetPin.setOnClickListener {
            val pin = mBinding.createPinOtpView.text.toString()
            if (pin.length != 4) {
                Toast.makeText(context, getString(R.string.pin_4_digits), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            BaseApp.getInstance().user?.let { mainUser ->
                mainUser.pin = pin.toInt()
                userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                    findNavController().navigate(R.id.action_createPinFragment_to_positiveTasksFragment)
                }
            }
        }
    }
}