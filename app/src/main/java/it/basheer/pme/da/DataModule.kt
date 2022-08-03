package it.basheer.pme.da

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.basheer.pme.data.AppDatabase
import it.basheer.pme.data.dao.UserDao
import it.basheer.pme.data.repo.UserRepo
import it.basheer.pme.util.AppSharedPref
import it.basheer.pme.util.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAppSharedPref(application: Application): AppSharedPref =
        AppSharedPref(application)
}