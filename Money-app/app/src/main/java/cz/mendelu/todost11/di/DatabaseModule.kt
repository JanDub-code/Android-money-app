package cz.mendelu.todost11.di

import android.content.Context
import cz.mendelu.todost11.database.BankDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BankDatabase {
        return BankDatabase.getDatabase(context)
    }
}