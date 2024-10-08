package com.example.cryptoscalpingapp.di

import android.content.Context
import com.example.cryptoscalpingapp.domain.usecase.apikeysecure.APIKeySecureRepository
import com.example.cryptoscalpingapp.domain.usecase.transaction.TransactionListRepository
import com.example.cryptoscalpingapp.domain.usecase.wallet.WalletListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWalletListRepository(@ApplicationContext context: Context): WalletListRepository {
        return com.example.cryptoscalpingapp.data.repository.WalletListRepositoryImpl(context)
    }

    @Provides
    fun provideTransactionListRepository(@ApplicationContext context: Context): TransactionListRepository {
        return com.example.cryptoscalpingapp.data.repository.TransactionListRepositoryImpl(context)
    }

    @Provides
    fun provideAPIKeySecureRepository(@ApplicationContext context: Context): APIKeySecureRepository {
        return com.example.cryptoscalpingapp.data.repository.ApiKeySecureRepositoryImp(context)
    }
}