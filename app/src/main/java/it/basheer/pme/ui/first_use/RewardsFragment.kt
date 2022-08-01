package it.basheer.pme.ui.first_use

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.databinding.FragmentRewardsBinding
import it.basheer.pme.ui.adapter.FirstPositiveTaskAdapter
import it.basheer.pme.ui.adapter.FirstRewardsTaskAdapter
import it.basheer.pme.ui.dialog.CreatePositiveTaskDialogFragment
import it.basheer.pme.ui.dialog.CreateRewardDialogFragment
import it.basheer.pme.ui.view_models.RewardViewModel
import it.basheer.pme.ui.view_models.TaskViewModel
import it.basheer.pme.util.POSITIVE_TASKS_TYPE
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class RewardsFragment : Fragment() {

    private lateinit var mBinding: FragmentRewardsBinding
    private lateinit var mFirstRewardsTaskAdapter: FirstRewardsTaskAdapter

    private val rewardViewModel: RewardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRewardsBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()

        mBinding.firstRewardsBtnAdd.setOnClickListener {
            CreateRewardDialogFragment.newInstance() { reward ->
                rewardViewModel.createReward(reward).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    reward.id = it
                    mFirstRewardsTaskAdapter.add(reward)
                    mBinding.firstRewardsRvRewards.showRecycler()
                }
            }.show(parentFragmentManager, CreateRewardDialogFragment.TAG)
        }

        mBinding.firstRewardsBtnNext.setOnClickListener {
            findNavController().navigate(R.id.action_rewardsFragment_to_mainFragment)
        }
    }

    private fun setupRecyclerView() {
        mBinding.firstRewardsRvRewards.apply {
            setLayoutManager(LinearLayoutManager(context))
            mFirstRewardsTaskAdapter = FirstRewardsTaskAdapter(requireContext())
            adapter = mFirstRewardsTaskAdapter
        }
    }

    private fun loadData() {
        mBinding.firstRewardsRvRewards.showProgress()
        rewardViewModel.getRewards(BaseApp.getInstance().getUser().value?.id!!).observe(viewLifecycleOwner) { tasks ->
            mFirstRewardsTaskAdapter.addAll(tasks)
            if (tasks.isEmpty()) {
                mBinding.firstRewardsRvRewards.showEmpty()
            }
        }
    }
}