package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reward")
data class Reward(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val name: String,
    val points: Int,
    val user_id: Long,
    var is_deleted: Boolean = false
)