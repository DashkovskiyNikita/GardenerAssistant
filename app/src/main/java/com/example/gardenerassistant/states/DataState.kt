package com.example.gardenerassistant.states



sealed class DataState<T>(val data : T? = null, val message : String? = null){
    class Loading<T>(data : T? = null) : DataState<T>(data)
    class Success<T>(data : T) : DataState<T>(data)
    class Error<T>(message: String,data : T? = null) : DataState<T>(data,message)
}
