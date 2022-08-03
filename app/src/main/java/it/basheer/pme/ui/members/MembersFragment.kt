package it.basheer.pme.ui.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.User
import it.basheer.pme.databinding.FragmentMembersBinding
import it.basheer.pme.ui.adapter.MembersAdapter
import it.basheer.pme.ui.view_models.UserViewModel

@AndroidEntryPoint
class MembersFragment : Fragment() {

    private lateinit var mBinding: FragmentMembersBinding
    private lateinit var mMembersAdapter: MembersAdapter

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMembersBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()

        mBinding.memberBtnCreate.setOnClickListener {
            val name = mBinding.memberTxtName.editText?.text
            if (name?.isEmpty() == true) {
                Toast.makeText(context, getString(R.string.warning_write_member_name), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val user = User(
                name = name.toString(),
                is_member = true,
                is_selected = false
            )
            userViewModel.createUser(user).observe(viewLifecycleOwner) { id ->
                val member = user.copy(id = id)
                mMembersAdapter.add(member)
                mBinding.memberRvMembers.showRecycler()
                mBinding.memberTxtName.editText?.setText("")
            }
        }
    }

    private fun setupRecyclerView() {
        mBinding.memberRvMembers.apply {
            setLayoutManager(LinearLayoutManager(context))
            mMembersAdapter = MembersAdapter(requireContext(), onDelete = { user ->
                user.is_deleted = true
                userViewModel.updateUser(user).observe(viewLifecycleOwner) {
                    loadData()
                }
            }, onChoose = { user ->
                val mainUser = BaseApp.getInstance().getUser().value
                mainUser?.is_selected = false
                mainUser?.let {
                    userViewModel.updateUser(it).observe(viewLifecycleOwner) {
                        user.is_selected = true
                        userViewModel.updateUser(user).observe(viewLifecycleOwner) {
                            BaseApp.getInstance().setUser(user)
                            loadData()
                        }
                    }
                }

            })
            adapter = mMembersAdapter
        }
    }

    private fun loadData() {
        mMembersAdapter.clear()
        mBinding.memberRvMembers.showProgress()
        userViewModel.getAllUsers().observe(viewLifecycleOwner) { users ->
            mMembersAdapter.addAll(users)
            if (users.isEmpty()) {
                mBinding.memberRvMembers.showEmpty()
            }
        }
    }
}