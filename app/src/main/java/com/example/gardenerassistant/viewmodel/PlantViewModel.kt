package com.example.gardenerassistant.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.usecase.AddNewPlantUseCase
import com.example.gardenerassistant.usecase.FetchPlantByIdUseCase
import com.example.gardenerassistant.usecase.UpdatePlantUseCase
import com.example.gardenerassistant.utils.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val addNewPlantUseCase: AddNewPlantUseCase,
    private val fetchPlantByIdUseCase: FetchPlantByIdUseCase,
    private val updatePlantUseCase: UpdatePlantUseCase
) : ViewModel() {

    private var _id : Int? = null
    private val _plantName = mutableStateOf("")
    private val _boardingTime = mutableStateOf(System.currentTimeMillis())
    private val _collectionTime = mutableStateOf<Long?>(null)
    private val _noteValue = mutableStateOf("")

    val plantName: State<String>
        get() = _plantName
    val boardingTime: State<Long>
        get() = _boardingTime
    val collectionTime: State<Long?>
        get() = _collectionTime
    val noteValue: State<String>
        get() = _noteValue


    private fun createPlantObject() : Plant{
        return Plant(
            id = _id,
            boardingTime = boardingTime.value,
            collectionTime = collectionTime.value!!,
            plantTitle = plantName.value,
            note = noteValue.value
        )
    }

    fun addNewPlant() {
        val plant = createPlantObject()
        viewModelScope.launch(Dispatchers.IO) {
            addNewPlantUseCase(plant)
        }
    }

    fun fetchPlantById(id: Int) {
        _id ?: viewModelScope.launch(Dispatchers.IO) {
            fetchPlantByIdUseCase(id).apply {
                _plantName.value = plantTitle
                _boardingTime.value = boardingTime
                _collectionTime.value = collectionTime
                _noteValue.value = note
                _id = this.id
            }
        }
    }

    fun updatePlant() {
        viewModelScope.launch(Dispatchers.IO) {
            val plant = createPlantObject()
            updatePlantUseCase(plant)
        }
    }

    fun onPlantNameChanged(value: String) {
        _plantName.value = value
    }

    fun onBoardingTimeChanged(value: Long) {
        _boardingTime.value = value
    }

    fun onCollectionTimeChanged(value: Long) {
        _collectionTime.value = value
    }

    fun onNoteValueChanged(value: String) {
        _noteValue.value = value
    }

}