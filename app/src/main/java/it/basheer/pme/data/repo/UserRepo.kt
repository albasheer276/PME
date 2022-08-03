package it.basheer.pme.data.repo

import it.basheer.pme.data.AppDatabase
import it.basheer.pme.data.model.User
import javax.inject.Inject

class UserRepo @Inject constructor(appDatabase: AppDatabase) {
    private val userDao = appDatabase.userDao

    suspend fun createUser(user: User): Long{
        return userDao.createUser(user)
    }

    suspend fun updateUser(user: User): Int {
        return userDao.updateUser(user)
    }

    suspend fun getSelectedUser(): User {
        return userDao.getSelectedUser()
    }

    suspend fun getUserUsedPoints(id: Long): Int {
        return userDao.getUserUsedPoints(id)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}