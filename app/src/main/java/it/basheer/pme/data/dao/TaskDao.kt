package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.User

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM tasks WHERE type = :type AND user_id = :userId")
    suspend fun getTasks(type: Int, userId: Long): List<Task>
}