package com.example.poo2.di


import com.example.poo2.ui.ContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel{ ContactViewModel() }
}