package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.RewardLog
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.User

@Dao
interface RewardDao {

    @Insert
    suspend fun createReward(reward: Reward): Long

    @Update
    suspend fun updateReward(reward: Reward)

    @Query(
        "SELECT * FROM reward WHERE user_id = :userId order by points asc"
    )
    suspend fun getRewards(userId: Long): List<Reward>

    @Insert
    suspend fun createRewardLog(rewardLog: RewardLog)

    @Query("Select * from rewards_log where reward_id = :id order by DATETIME(date) desc limit 5")
    suspend fun getLastProgress(id: Long): List<RewardLog>
}