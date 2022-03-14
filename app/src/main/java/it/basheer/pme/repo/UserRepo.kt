package it.basheer.pme.repo

import it.basheer.pme.dao.UserDao
import it.basheer.pme.model.User
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    suspend fun test(user: User){
        userDao.test(user)
    }

}