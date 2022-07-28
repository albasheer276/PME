package it.basheer.pme.data.repo

import it.basheer.pme.data.AppDatabase
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import javax.inject.Inject

class RewardRepo @Inject constructor(appDatabase: AppDatabase) {
    private val rewardRepo = appDatabase.rewardDao

    suspend fun createReward(reward: Reward): Long {
        return rewardRepo.createReward(reward)
    }

    suspend fun updateReward(reward: Reward) {
        rewardRepo.updateReward(reward)
    }

    suspend fun getReward(userId: Long): List<Reward> {
        return rewardRepo.getRewards(userId)
    }

}