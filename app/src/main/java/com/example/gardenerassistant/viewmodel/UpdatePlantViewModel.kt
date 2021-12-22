package com.example.gardenerassistant.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.usecase.FetchPlantByIdUseCase
import com.example.gardenerassistant.usecase.UpdatePlantUseCase
import com.example.gardenerassistant.utils.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePlantViewModel @Inject constructor(
    private val fetchPlantByIdUseCase: FetchPlantByIdUseCase,
    private val updatePlantUseCase: UpdatePlantUseCase
) : ViewModel() {

    private val _plant = mutableStateOf<Plant?>(null)
    val plant: State<Plant?>
        get() = _plant

    fun fetchPlantById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _plant.value = fetchPlantByIdUseCase(id)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePlantUseCase(plant)
        }
    }

    fun updatePlantData(plant : Plant){
        _plant.value = plant
    }
}