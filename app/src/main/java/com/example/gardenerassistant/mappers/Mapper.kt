package com.example.gardenerassistant.mappers

interface Mapper<Input,Output> {
    fun map(input : Input) : Output
}