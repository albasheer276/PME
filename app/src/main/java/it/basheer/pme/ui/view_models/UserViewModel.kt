package it.basheer.pme.ui.view_models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.User
import it.basheer.pme.data.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getParentUser() = liveData {
        emit(userRepo.getParentUser())
    }

    fun getUserUsedPoints(id: Long) = liveData {
        emit(userRepo.getUserUsedPoints(id))
    }
}