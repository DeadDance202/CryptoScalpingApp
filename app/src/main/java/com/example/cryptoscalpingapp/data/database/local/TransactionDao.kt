package com.example.cryptoscalpingapp.data.database.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransactionItem(transactionItem: TransactionItem)

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionItem>
}