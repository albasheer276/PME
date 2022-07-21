package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String? = null,
    val pin: Int? = null,
    val email: String? = null,
    val is_child: Boolean? = null,
    val points: Int? = null
)