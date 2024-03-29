package it.basheer.pme.ui.first_use

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.FragmentCreateProfileBinding
import it.basheer.pme.ui.view_models.UserViewModel

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentCreateProfileBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handle the back pressed action, to close the app, and do not open the splash screen again
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCreateProfileBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.createProfileTxtName.requestFocus()

        mBinding.createProfileBtnCreate.setOnClickListener {
            createProfile()
        }

        mBinding.createProfileTxtName.editText?.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    createProfile()
                    true
                }
                else -> false
            }
        }
    }

    private fun createProfile() {
        val name = mBinding.createProfileTxtName.editText?.text
        if (name?.isEmpty() == true) {
            Toast.makeText(context, getString(R.string.warning_write_your_name), Toast.LENGTH_LONG).show()
            return
        }
        val user = User(
            name = name.toString(),
            is_member = false,
            is_selected = true
        )
        userViewModel.createUser(user).observe(viewLifecycleOwner) { id ->
            BaseApp.getInstance().setUser(user.copy(id = id))
            findNavController().navigate(R.id.action_createProfileFragment_to_createPinFragment)
        }
    }
}