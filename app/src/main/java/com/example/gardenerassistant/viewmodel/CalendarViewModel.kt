package com.example.gardenerassistant.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.states.CalendarScreenState
import com.example.gardenerassistant.usecase.FetchMonthDaysUseCase
import com.example.gardenerassistant.utils.DateHelper
import com.example.gardenerassistant.utils.Day
import com.example.gardenerassistant.utils.Plant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(private val fetchMonthDaysUseCase: FetchMonthDaysUseCase) :
    ViewModel() {
    private val _localDate = MutableLiveData(LocalDate.now())
    private val _year = MutableLiveData(2020)
    private val _month = MutableLiveData(1)
    private val _monthDays = MutableLiveData<List<Day>>()
    private val _screenState = MutableLiveData(CalendarScreenState())

    val year: LiveData<Int> = _year
    val month: LiveData<Int> = _month
    val monthDays: LiveData<List<Day>> = _monthDays
    val screenState : LiveData<CalendarScreenState> = _screenState

    init {
        updateData()
    }

    fun onNextMonthClicked() {
        _localDate.value = _localDate.value?.plusMonths(1)
        updateData()
    }

    fun onPreviousMonthClicked() {
        _localDate.value = _localDate.value?.minusMonths(1)
        updateData()
    }

    private fun fetchMonthDays() {
        val start = DateHelper.getFirstDayMillisByLocalDate(_localDate.value!!)
        val end = DateHelper.getLastDayMillisByNumber(_localDate.value!!)
        viewModelScope.launch {
            _screenState.value = CalendarScreenState(isLoading = true)
            val data = fetchMonthDaysUseCase(start, end)
            _screenState.value = CalendarScreenState(data = data)
        }
    }

    private fun getMonthDaysByNumber() {
        val days = DateHelper.getMonthDaysByDate(_localDate.value!!)
        _monthDays.value = days
    }

    private fun updateData() {
        val localDate = _localDate.value
        _year.value = localDate?.year
        _month.value = localDate?.month?.value
        getMonthDaysByNumber()
        fetchMonthDays()
    }
}