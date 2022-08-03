package it.basheer.pme.ui.view_models

import android.app.Application
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.User
import it.basheer.pme.data.repo.UserRepo
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application, private val userRepo: UserRepo
) : BaseViewModel(application) {

    fun createUser(user: User) = liveData {
        emit(userRepo.createUser(user))
    }

    fun updateUser(user: User) = liveData {
        emit(userRepo.updateUser(user))
    }

    fun getSelectedUser() = liveData {
        emit(userRepo.getSelectedUser())
    }

    fun getUserUsedPoints(id: Long) = liveData {
        emit(userRepo.getUserUsedPoints(id))
    }

    fun getAllUsers() = liveData {
        emit(userRepo.getAllUsers())
    }
}