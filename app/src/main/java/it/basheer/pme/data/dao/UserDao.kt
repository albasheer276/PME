package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import it.basheer.pme.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun createUser(user: User): Long

    @Update
    suspend fun updateUser(user: User): Int

    @Query("SELECT * FROM user where is_child = 0")
    suspend fun getParentUser(): User

    @Query("SELECT sum(points) FROM rewards_log where user_id = :id")
    suspend fun getUserUsedPoints(id: Long): Int
}