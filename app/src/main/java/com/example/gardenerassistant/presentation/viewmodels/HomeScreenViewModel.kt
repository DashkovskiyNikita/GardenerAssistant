package com.example.gardenerassistant.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.usecases.IDeleteUserPlantUseCase
import com.example.gardenerassistant.domain.usecases.IGetUserPlantsUseCase
import com.example.gardenerassistant.presentation.models.states.HomeScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class IHomeScreenViewModel(
    initialState: HomeScreenState
) : MVIViewModel<HomeScreenState>(initialState) {
    abstract fun deletePlant(plant: UserPlantModel)
}


class HomeScreenViewModel(
    private val getUserPlantsUseCase: IGetUserPlantsUseCase,
    private val deleteUserPlantUseCase: IDeleteUserPlantUseCase
) : IHomeScreenViewModel(HomeScreenState()) {

    init {
        fetchAllPlants()
    }

    override fun deletePlant(plant: UserPlantModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserPlantUseCase(plant)
        }
    }

    private fun fetchAllPlants() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserPlantsUseCase(Unit).collect { plants ->
                reduce { state.copy(isLoading = false, data = plants) }
            }
        }
    }
}