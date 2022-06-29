package com.example.gardenerassistant.data.mappers

interface Mapper<in Input, out Output> {
    operator fun invoke(input: Input): Output
}