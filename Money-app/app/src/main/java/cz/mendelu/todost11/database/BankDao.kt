package cz.mendelu.todost11.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.model.UserSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {

    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("DELETE FROM transactions")
    fun deleteAllTransactions(): Void

    @Insert
    suspend fun insertTransaction(purchase: Transaction): Long

    @Update
    suspend fun updateTransactions(purchase: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransaction(id: Long): Transaction

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Long)

    @Query("SELECT * FROM transactions WHERE date >= :from AND date <= :to ")
    fun getTransactionsByMonth(from: Long, to: Long): Flow<List<Transaction>>



    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getAllSettings(): Flow<List<UserSettings>>

    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getAllSettingsMain(): UserSettings

    @Query("SELECT `paylimit` FROM user_settings WHERE id = 1")
    suspend fun getLimitSettings(): Long

    @Query("SELECT photo FROM user_settings WHERE id = 1")
    suspend fun getPhoto(): String?

    @Update
    suspend fun updateSettings(settings: UserSettings)

    @Insert
    suspend fun insertSettings(settings: UserSettings): Long

    @Query("UPDATE user_settings SET photo=:photoUri WHERE id = 1")
    suspend fun setPhoto(photoUri: String)

}