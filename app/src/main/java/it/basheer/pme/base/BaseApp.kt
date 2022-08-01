package it.basheer.pme.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import it.basheer.pme.data.model.User

@HiltAndroidApp
class BaseApp : Application() {

    private val user: MutableLiveData<User> = MutableLiveData<User>()

    fun getUser(): LiveData<User> = user

    fun setUser(user: User) {
        this.user.postValue(user)
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private lateinit var instance: BaseApp

        fun getInstance() = instance
    }
}