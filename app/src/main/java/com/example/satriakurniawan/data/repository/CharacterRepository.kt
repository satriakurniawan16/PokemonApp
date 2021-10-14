package com.example.satriakurniawan.data.repository

import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.data.local.CharacterDao
import com.example.satriakurniawan.data.remote.CharacterRemoteDataSource
import com.example.satriakurniawan.utils.performGetLocalOnly
import com.example.satriakurniawan.utils.performGetOperation
import com.example.satriakurniawan.utils.performGetRemoteOnly
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: String) = performGetRemoteOnly(
        networkCall = { remoteDataSource.getCharacter(id) }
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = { localDataSource.getAllCharacters() },
        networkCall = { remoteDataSource.getCharacters() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    fun getMyCharacters() = performGetLocalOnly { localDataSource.getCatchedPokemon() }

    fun insertMyPokemon(character : MyCharacter) = localDataSource.insertMyPokemon(character)
    fun updateMyPokemon(character : MyCharacter) = localDataSource.updateMyPokemon(character)

}