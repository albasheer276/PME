package it.basheer.pme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val pin: Int? = null
)