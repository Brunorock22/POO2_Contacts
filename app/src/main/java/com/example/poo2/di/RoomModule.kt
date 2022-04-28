package com.example.poo2.di

import android.content.Context
import androidx.room.Room
import com.example.poo2.data.ContactDAO
import com.example.poo2.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ContactDatabase{
        return Room.databaseBuilder(context, ContactDatabase::class.java,ContactDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideContactDao(database: ContactDatabase): ContactDAO = database.contactDAO
}