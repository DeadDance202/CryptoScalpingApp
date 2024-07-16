package com.example.cryptoscalpingapp.data.database.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//TODO: add FLOW in future
@Dao
interface WalletDao {
    @Insert
    suspend fun insertWalletItem(walletItem: WalletItem)

    @Query("SELECT * FROM wallets")
    fun getAllWalletItems(): LiveData<List<WalletItem>>

    @Query("SELECT * FROM wallets WHERE id = :walletItemId")
    suspend fun getWalletItem(walletItemId: Int): WalletItem

    @Delete
    suspend fun deleteWalletItem(walletItem: WalletItem)

    @Update
    suspend fun updateWalletItem(walletItem: WalletItem)
}