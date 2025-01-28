package cz.mendelu.todost11.database

import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface IBankRepository {

    fun getAllSettings(): Flow<List<UserSettings>>
    fun getAllSettingsMain(): UserSettings


    suspend fun getLimitSettings(): Long

    suspend fun getPhoto(): String?

    suspend fun updateSettings(settings: UserSettings)

    suspend fun insertSettings(settings: UserSettings): Long

    suspend fun setPhoto(photoUri: String)

    fun getAllTransactions(): Flow<List<Transaction>>
    fun deleteAllTransactions(): Void
    suspend fun insertTransaction(transaction: Transaction): Long

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun getTransaction(id: Long): Transaction

    suspend fun deleteTransaction(id: Long)


    fun getTransactionsByMonth(from: Long, to: Long): Flow<List<Transaction>>


}