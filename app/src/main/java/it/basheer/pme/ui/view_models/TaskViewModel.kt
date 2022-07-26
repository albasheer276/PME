package it.basheer.pme.ui.view_models

import android.app.Application
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.Task
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
}