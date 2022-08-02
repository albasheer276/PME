package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "rewards_log")
data class RewardLog(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val user_id: Long,
    val reward_id: Long,
    val points: Int,
    val date: String,
)