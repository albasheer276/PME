package it.basheer.pme.ui.view_models

import android.app.Application
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.Task
import it.basheer.pme.data.model.TaskLog
import it.basheer.pme.data.repo.TaskRepo
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    application: Application, private val taskRepo: TaskRepo
) : BaseViewModel(application) {

    fun createTask(task: Task) = liveData {
        emit(taskRepo.createTask(task))
    }

    fun updateTask(task: Task) = liveData {
        emit(taskRepo.updateTask(task))
    }

    fun getTasks(type: Int, userId: Long) = liveData {
        emit(taskRepo.getTasks(type, userId))
    }

    fun getActiveTasks(type: Int, userId: Long, date: String, startWeek: String, startMonth: String) = liveData {
        val tasks = taskRepo.getActiveTasks(type, userId, date, startWeek, startMonth)
        tasks.map { task ->
            task.status = if (task.completed == 0) 0 else if (task.completed!! < task.count!!) 1 else 2
        }
        emit(tasks)
    }

    fun createTaskLog(taskLog: TaskLog) = liveData {
        emit(taskRepo.createTaskLog(taskLog))
    }
}