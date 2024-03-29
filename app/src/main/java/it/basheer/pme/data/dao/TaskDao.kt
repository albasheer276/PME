package it.basheer.pme.data.dao

import androidx.room.*
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.TaskLog

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
                "WHEN 'Weekly' THEN (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and  Datetime(date) >= Datetime(:startWeek)) " +
                "WHEN 'Monthly' THEN (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and Datetime(date) >= Datetime(:startMonth)) " +
                "ELSE (SELECT COUNT(*) FROM tasks_log where task_id = tasks.id and Datetime(date) >= Datetime(:date)) " +
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
    suspend fun getActiveTasks(type: Int, userId: Long, date: String, startWeek: String, startMonth: String): List<ActiveTask>

    @Query(
        "SELECT * FROM tasks_log where task_id = :taskId and " +
                "((:period = 'Weekly' and Datetime(date) >= Datetime(:startWeek)) or " +
                "(:period = 'Monthly' and Datetime(date) >= Datetime(:startMonth)) or " +
                "(Datetime(date) >= Datetime(:date)))" +
                " order by Datetime(date) desc"
    )
    suspend fun getCurrentProgress(taskId: Long, period: String, date: String, startWeek: String, startMonth: String): List<TaskLog>

    @Insert
    suspend fun createTaskLog(taskLog: TaskLog)

    @Delete
    suspend fun deleteTaskLog(taskLog: TaskLog)
}