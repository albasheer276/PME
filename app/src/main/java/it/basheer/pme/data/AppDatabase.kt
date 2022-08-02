package it.basheer.pme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import it.basheer.pme.data.dao.RewardDao
import it.basheer.pme.data.dao.TaskDao
import it.basheer.pme.data.dao.UserDao
import it.basheer.pme.data.model.*

@Database(entities = [User::class, Task::class, Reward::class, TaskLog::class, RewardLog::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val taskDao: TaskDao
    abstract val rewardDao: RewardDao
}