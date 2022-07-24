package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val period: String,
    val count: Int? = null,
    val duration: Int? = null,
    val points: Int,
    val type: Int,
    val user_id: Int,
)