package com.example.cryptoscalpingapp.data.database.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactionItem(transactionItem: TransactionItem)

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    fun getAllTransactionsDesc(): LiveData<List<TransactionItem>>

    @Query("SELECT * FROM transactions WHERE walletItemId = :walletItemId ORDER BY id DESC")
    fun getAllTransactionsByWalletIdDesc(walletItemId: Int): LiveData<List<TransactionItem>>

    @Query("DELETE FROM transactions WHERE walletItemId = :walletItemId")
    fun deleteAllTransactionsByWalletId(walletItemId: Int)
}