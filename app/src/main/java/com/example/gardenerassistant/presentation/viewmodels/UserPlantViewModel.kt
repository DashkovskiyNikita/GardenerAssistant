package com.example.gardenerassistant.presentation.viewmodels

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.data.sources.IPlantsDataSource
import com.example.gardenerassistant.domain.usecases.*
import com.example.gardenerassistant.data.sources.AbstractImageStorage
import com.example.gardenerassistant.presentation.models.intents.UserPlantScreenIntent
import com.example.gardenerassistant.presentation.models.states.UserPlantState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

abstract class IUserPlantViewModel(
    initialState: UserPlantState
) : MVIViewModel<UserPlantState>(initialState), EventListener<UserPlantScreenIntent> {
    abstract fun fetchUserPlant(id: Int, context: Context)
    abstract fun insertUserPlant(isUpdate: Boolean)
    abstract fun saveImage(context: Context, uri: Uri)
    abstract fun deleteImage(context: Context)
}

class UserPlantViewModel(
    private val getUserPlantUseCase: IGetUserPlantUseCase,
    private val createUserPlantUseCase: ICreateUserPlantUseCase,
    private val updateUserPlantUseCase: IUpdateUserPlantUseCase,
    private val plantsDataSource: IPlantsDataSource,
    private val imageStorage: AbstractImageStorage
) : IUserPlantViewModel(UserPlantState()) {

    private val plantsInfo by lazy { plantsDataSource.fetchAllPlants() }

    init {
        reduce {
            state.copy(
                plantsSuggestions = plantsInfo.map { it.name }.distinct(),
                sortsSuggestions = plantsInfo.map { it.sort }
            )
        }
    }

    override fun onEvent(event: UserPlantScreenIntent) {
        when (event) {
            is UserPlantScreenIntent.NameChanged -> nameChanged(event.value)
            is UserPlantScreenIntent.SortChanged -> sortChanged(event.value)
            is UserPlantScreenIntent.NoteChanged -> noteChanged(event.value)
            is UserPlantScreenIntent.BoardingTimeChanged -> boardingTimeChanged(event.value)
            is UserPlantScreenIntent.CollectionTimeChanged -> collectionTimeChanged(event.value)
        }
    }

    override fun fetchUserPlant(id: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val plant = getUserPlantUseCase(id)
            reduce { state.copy(userPlantModel = plant) }
            val image = plant.image?.let { imageStorage.selectImage(it) }
            reduce { state.copy(bitmap = image) }
        }
    }

    override fun insertUserPlant(isUpdate: Boolean) {
        viewModelScope.launch {
            when (isUpdate) {
                true -> updateUserPlantUseCase(state.userPlantModel)
                false -> createUserPlantUseCase(state.userPlantModel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun saveImage(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            reduce { state.copy(bitmapLoading = true) }
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            reduce { state.copy(bitmap = bitmap, bitmapLoading = false) }
            val fileName = imageStorage.saveImage(bitmap)
            viewModelState.value.userPlantModel.image = fileName
        }
    }

    override fun deleteImage(context: Context) {
        viewModelScope.launch {
            reduce {
                state.copy(
                    userPlantModel = state.userPlantModel.copy(image = null),
                    bitmap = null
                )
            }
        }
    }

    private fun nameChanged(value: String) {
        reduce { state.copy(userPlantModel = state.userPlantModel.copy(name = value)) }
        val suggestions = plantsInfo.filter { it.name == value }.map { it.sort }
        reduce { state.copy(sortsSuggestions = suggestions) }
    }

    private fun sortChanged(value: String) {
        reduce { state.copy(userPlantModel = state.userPlantModel.copy(sort = value)) }
        val growingTime = plantsInfo.find { it.sort == value }?.growingTime
        growingTime?.let {
            val boardingTime = LocalDate.parse(state.userPlantModel.boardingTime)
            val collectionTime = boardingTime.plusDays(growingTime.toLong()).toString()
            reduce {
                state.copy(
                    userPlantModel = state.userPlantModel.copy(collectionTime = collectionTime)
                )
            }
        }
    }


    private fun noteChanged(value: String) = reduce {
        state.copy(userPlantModel = state.userPlantModel.copy(note = value))
    }

    private fun boardingTimeChanged(value: String) = reduce {
        state.copy(userPlantModel = state.userPlantModel.copy(boardingTime = value))
    }

    private fun collectionTimeChanged(value: String) = reduce {
        state.copy(userPlantModel = state.userPlantModel.copy(collectionTime = value))
    }
}