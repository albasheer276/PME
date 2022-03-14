package it.basheer.pme.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.basheer.pme.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun test(user: User)
}