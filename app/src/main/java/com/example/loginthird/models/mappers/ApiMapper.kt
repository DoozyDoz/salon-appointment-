package com.example.loginthird.models.mappers

interface ApiMapper<E, D> {

  fun mapToDomain(apiEntity: E): D
}