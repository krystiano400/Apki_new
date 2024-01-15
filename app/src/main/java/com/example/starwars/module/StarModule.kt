package com.example.starwars.module

import MainViewModel
import PeopleDetailsRepository
import PeopleRepository
import com.example.starwars.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val starModule = module {
   single { PeopleRepository() }
    single { PeopleDetailsRepository() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

}