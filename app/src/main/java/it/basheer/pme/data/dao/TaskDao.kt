package it.basheer.pme.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import it.basheer.pme.data.model.ActiveTasks
import it.basheer.pme.data.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun createTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM tasks WHERE type = :type AND user_id = :userId")
    suspend fun getTasks(type: Int, userId: Long): List<Task>

    @Query(
        "SELECT *, case period " +
                "WHEN 'Weekly' THEN (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and  date >= :startWeek) " +
                "WHEN 'Monthly' THEN (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and date >= :startMonth) " +
                "ELSE (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and date >= :date) " +
                "END as completed " +
                "FROM tasks where user_id = :userId and type = :type " +
                "ORDER BY" +
                "  CASE" +
                "    WHEN completed == 0 THEN 0" +
                "    WHEN completed < count THEN 1" +
                "    WHEN completed == count THEN 2" +
                "    ELSE 2" +
                "  END" +
                " , " +
                "  CASE period" +
                "    WHEN 'Daily' THEN 0" +
                "    WHEN 'Weekly' THEN 1" +
                "    WHEN 'Monthly' THEN 2" +
                "    ELSE 2" +
                "  END"
    )
    suspend fun getActiveTasks(type: Int, userId: Long, date: String, startWeek: String, startMonth: String): List<ActiveTasks>
}