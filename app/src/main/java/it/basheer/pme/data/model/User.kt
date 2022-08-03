package it.basheer.pme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    var is_member: Boolean = true,
    var points: Int = 0,
    var is_selected: Boolean = false,
    var is_deleted: Boolean = false
)