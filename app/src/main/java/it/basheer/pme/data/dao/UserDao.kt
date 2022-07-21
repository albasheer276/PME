package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.basheer.pme.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun createUser(user: User): Long
}