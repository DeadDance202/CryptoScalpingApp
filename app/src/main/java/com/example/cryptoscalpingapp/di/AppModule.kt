package com.example.cryptoscalpingapp.di

import android.content.Context
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
}