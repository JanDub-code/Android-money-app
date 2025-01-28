package cz.mendelu.todost11.di

import cz.mendelu.todost11.database.IBankRepository
import cz.mendelu.todost11.database.BankRepositoryImpl
import cz.mendelu.todost11.database.BankDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalTasksRepository(dao: BankDao): IBankRepository {
        return BankRepositoryImpl(dao)
    }

}