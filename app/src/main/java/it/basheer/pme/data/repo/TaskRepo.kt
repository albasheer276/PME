package it.basheer.pme.data.repo

import it.basheer.pme.data.AppDatabase
import it.basheer.pme.data.model.ActiveTask
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.TaskLog
import javax.inject.Inject

class TaskRepo @Inject constructor(appDatabase: AppDatabase) {
    private val taskDao = appDatabase.taskDao

    suspend fun createTask(task: Task): Long{
        return taskDao.createTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun getTasks(type: Int, userId: Long): List<Task> {
        return taskDao.getTasks(type, userId)
    }

    suspend fun getActiveTasks(type: Int, userId: Long, date: String, startWeek: String, startMonth: String): List<ActiveTask> {
        return taskDao.getActiveTasks(type, userId, date, startWeek, startMonth)
    }

    suspend fun createTaskLog(taskLog: TaskLog) {
        return taskDao.createTaskLog(taskLog)
    }

}