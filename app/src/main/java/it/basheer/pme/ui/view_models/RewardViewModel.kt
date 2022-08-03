package it.basheer.pme.ui.view_models

import android.app.Application
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.RewardLog
import it.basheer.pme.data.repo.RewardRepo
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    application: Application, private val rewardRepo: RewardRepo
) : BaseViewModel(application) {

    fun createReward(reward: Reward) = liveData {
        emit(rewardRepo.createReward(reward))
    }

    fun updateRewards(reward: Reward) = liveData {
        emit(rewardRepo.updateReward(reward))
    }

    fun getRewards(userId: Long) = liveData {
        emit(rewardRepo.getReward(userId))
    }

    fun createRewardLog(rewardLog: RewardLog) = liveData {
        emit(rewardRepo.createRewardLog(rewardLog))
    }

    fun getLastProgress(id: Long) = liveData {
        emit(rewardRepo.getLastProgress(id))
    }
}