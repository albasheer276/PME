package it.basheer.pme.da

import androidx.room.Database
import androidx.room.RoomDatabase
import it.basheer.pme.dao.UserDao
import it.basheer.pme.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}