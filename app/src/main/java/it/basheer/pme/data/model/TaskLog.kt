package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_log")
data class TaskLog(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val user_id: Long,
    val task_id: Long,
    val points: Int,
    val date: String,
)