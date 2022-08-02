package it.basheer.pme.ui.reward

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.basheer.pme.R
import it.basheer.pme.base.BaseApp
import it.basheer.pme.data.model.RewardLog
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.databinding.FragmentRewardsBinding
import it.basheer.pme.ui.adapter.FirstRewardsAdapter
import it.basheer.pme.ui.adapter.RewardsAdapter
import it.basheer.pme.ui.dialog.CreateRewardDialogFragment
import it.basheer.pme.ui.dialog.PositiveTaskDialogFragment
import it.basheer.pme.ui.dialog.RewardDialogFragment
import it.basheer.pme.ui.view_models.RewardViewModel
import it.basheer.pme.ui.view_models.UserViewModel
import it.basheer.pme.util.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RewardsFragment : Fragment() {

    private lateinit var mBinding: FragmentRewardsBinding
    private lateinit var mRewardsAdapter: RewardsAdapter

    private val rewardViewModel: RewardViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

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

        mBinding.rewardsBtnAdd.setOnClickListener {
            CreateRewardDialogFragment.newInstance() { reward ->
                rewardViewModel.createReward(reward).observe(viewLifecycleOwner) {
                    Toast.makeText(context, getString(R.string.create_task_success), Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    loadData()
                }
            }.show(parentFragmentManager, CreateRewardDialogFragment.TAG)
        }
    }

    private fun setupRecyclerView() {
        mBinding.rewardsRvRewards.apply {
            setLayoutManager(LinearLayoutManager(context))
            mRewardsAdapter = RewardsAdapter(requireContext()) { reward ->
                RewardDialogFragment.newInstance(reward) {
                    val date = Date()
                    val currentDate = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date)
                    val rewardLog = RewardLog(
                        user_id = BaseApp.getInstance().getUser().value?.id!!,
                        reward_id = reward.id!!,
                        points = reward.points,
                        date = currentDate
                    )
                    rewardViewModel.createRewardLog(rewardLog).observe(viewLifecycleOwner) {

                        BaseApp.getInstance().getUser().value?.let { mainUser ->
                            mainUser.points = mainUser.points.minus(reward.points)
                            BaseApp.getInstance().setUser(mainUser)
                            userViewModel.updateUser(mainUser).observe(viewLifecycleOwner) {
                                loadData()
                            }
                        }
                    }

                }.show(parentFragmentManager, RewardDialogFragment.TAG)
            }
            adapter = mRewardsAdapter

            setRefreshListener {
                setRefreshing(true)
                loadData()
            }
        }
    }

    private fun loadData() {
        mRewardsAdapter.clear()
        mBinding.rewardsRvRewards.showProgress()
        rewardViewModel.getRewards(BaseApp.getInstance().getUser().value?.id!!).observe(viewLifecycleOwner) { tasks ->
            mBinding.rewardsRvRewards.setRefreshing(false)
            mRewardsAdapter.addAll(tasks)
            if (tasks.isEmpty()) {
                mBinding.rewardsRvRewards.showEmpty()
            }
        }
    }
}