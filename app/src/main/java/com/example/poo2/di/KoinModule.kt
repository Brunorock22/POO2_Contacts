package com.example.poo2.di


import android.app.Application
import androidx.room.Room
import com.example.poo2.data.ContactDAO
import com.example.poo2.data.ContactDatabase
import com.example.poo2.ui.ContactViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel{ ContactViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ContactDatabase::class.java, "database-wizard"
        ).build()
    }

    fun provideCountriesDao(database: ContactDatabase): ContactDAO {
        return  database.contactDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}