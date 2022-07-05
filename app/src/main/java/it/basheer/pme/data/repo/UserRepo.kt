package it.basheer.pme.data.repo

import it.basheer.pme.data.AppDatabase
import it.basheer.pme.data.model.User
import javax.inject.Inject

class UserRepo @Inject constructor(appDatabase: AppDatabase) {
    private val userDao = appDatabase.userDao

    suspend fun createUser(user: User){
        userDao.createUser(user)
    }

}