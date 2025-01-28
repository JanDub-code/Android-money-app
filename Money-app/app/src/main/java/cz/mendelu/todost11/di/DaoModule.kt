package cz.mendelu.todost11.di

import cz.mendelu.todost11.database.BankDao
import cz.mendelu.todost11.database.BankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideTasksDao(database: BankDatabase): BankDao {
        return database.tasksDao()
    }
}