package it.basheer.pme.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.FragmentProfileBinding
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = BaseApp.getInstance().getUser().value

        mBinding.apply {
            profileTxtName.editText?.setText(user?.name)
            profileTxtCurrentPoints.text = "${user?.points.toString()} ${getString(R.string.pt)}"

            userViewModel.getUserUsedPoints(user?.id!!).observe(viewLifecycleOwner) { usedPoints ->
                profileTxtUsedPoints.text = "$usedPoints ${getString(R.string.pt)}"
                profileTxtTotalPoints.text = "${usedPoints + user.points} ${getString(R.string.pt)}"
            }
        }

        mBinding.profileBtnChange.setOnClickListener {
            val name = mBinding.profileTxtName.editText?.text
            if (name?.isEmpty() == true) {
                Toast.makeText(context, getString(R.string.warning_write_your_name), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val newUser = user?.copy(name = name.toString()) ?: return@setOnClickListener
            userViewModel.updateUser(newUser).observe(viewLifecycleOwner) {
                BaseApp.getInstance().setUser(newUser)
                hideKeyboard()
                Toast.makeText(context, getString(R.string.name_chnaged_success), Toast.LENGTH_SHORT).show()
            }
        }
    }
}