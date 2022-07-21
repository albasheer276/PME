package it.basheer.pme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import it.basheer.pme.data.dao.UserDao
import it.basheer.pme.data.model.User

@Database(entities = [User::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}