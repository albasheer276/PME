package it.basheer.pme.ui.first_use

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
import it.basheer.pme.databinding.FragmentFirstRewardsBinding
import it.basheer.pme.ui.adapter.FirstRewardsAdapter
import it.basheer.pme.ui.dialog.CreateRewardDialogFragment
import it.basheer.pme.ui.view_models.RewardViewModel
import it.basheer.pme.util.hideKeyboard

@AndroidEntryPoint
class FirstRewardsFragment : Fragment() {

    private lateinit var mBinding: FragmentFirstRewardsBinding
    private lateinit var mFirstRewardsAdapter: FirstRewardsAdapter

    private val rewardViewModel: RewardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFirstRewardsBinding.inflate(inflater)
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
                    mFirstRewardsAdapter.add(reward)
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
            mFirstRewardsAdapter = FirstRewardsAdapter(requireContext())
            adapter = mFirstRewardsAdapter
        }
    }

    private fun loadData() {
        mBinding.firstRewardsRvRewards.showProgress()
        rewardViewModel.getRewards(BaseApp.getInstance().getUser().value?.id!!).observe(viewLifecycleOwner) { tasks ->
            mFirstRewardsAdapter.addAll(tasks)
            if (tasks.isEmpty()) {
                mBinding.firstRewardsRvRewards.showEmpty()
            }
        }
    }
}