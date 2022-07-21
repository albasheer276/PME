package it.basheer.pme.base

import android.app.Application
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import it.basheer.pme.data.model.User

@HiltAndroidApp
class BaseApp : Application() {

    var user: User? = null


    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private lateinit var instance: BaseApp

        fun getInstance() = instance
    }
}