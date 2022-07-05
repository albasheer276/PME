package it.basheer.pme.ui

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.basheer.pme.base.BaseViewModel
import it.basheer.pme.data.model.User
import it.basheer.pme.data.repo.UserRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    application: Application, private val userRepo: UserRepo
) : BaseViewModel(application) {

    fun createUser(user: User) {
        viewModelScope.launch {
            userRepo.createUser(user)
        }
    }
}