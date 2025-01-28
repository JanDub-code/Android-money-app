package cz.mendelu.todost11.database

import android.util.Log
import cz.mendelu.todost11.model.Transaction
import cz.mendelu.todost11.model.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(private val dao: BankDao) : IBankRepository {

    override fun getAllSettings(): Flow<List<UserSettings>> {
        return dao.getAllSettings()
    }

    override fun getAllSettingsMain(): UserSettings {
        return  dao.getAllSettingsMain()
    }

    override suspend fun getLimitSettings(): Long {
        return dao.getLimitSettings()
    }

    override suspend fun getPhoto(): String? {
        return dao.getPhoto()
    }

    override suspend fun updateSettings(settings: UserSettings) {
        return dao.updateSettings(settings)
    }

    override suspend fun insertSettings(settings: UserSettings): Long {
        return dao.insertSettings(settings)
    }

    override suspend fun setPhoto(photoUri: String) {
        return dao.setPhoto(photoUri)
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions()
    }

    override fun deleteAllTransactions(): Void {
        return dao.deleteAllTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction): Long {
        return dao.insertTransaction(transaction)
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        return dao.updateTransactions(transaction)
    }

    override suspend fun getTransaction(id: Long): Transaction {
        return dao.getTransaction(id)
    }

    override suspend fun deleteTransaction(id: Long) {
        return dao.deleteTransaction(id)
    }

    override fun getTransactionsByMonth(from: Long, to: Long): Flow<List<Transaction>> {
        return dao.getTransactionsByMonth(from,to)
    }


}