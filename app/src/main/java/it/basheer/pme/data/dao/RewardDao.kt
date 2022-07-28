package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import it.basheer.pme.data.model.Reward
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.User

@Dao
interface RewardDao {

    @Insert
    suspend fun createReward(reward: Reward): Long

    @Update
    suspend fun updateReward(reward: Reward)

    @Query("SELECT * FROM reward WHERE user_id = :userId")
    suspend fun getRewards(userId: Long): List<Reward>
}