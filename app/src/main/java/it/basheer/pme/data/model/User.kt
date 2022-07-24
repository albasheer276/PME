package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String? = null,
    var pin: Int? = null,
    var email: String? = null,
    var is_child: Boolean = true,
    var points: Int = 0
)