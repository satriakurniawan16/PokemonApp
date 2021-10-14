package com.example.satriakurniawan.data.remote

import com.example.satriakurniawan.data.entities.CharacterDetailList
import com.example.satriakurniawan.data.entities.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("pokemon")
    suspend fun getAllCharacters() : Response<CharacterList>

    @GET("pokemon/{name}")
    suspend fun getCharacter(@Path("name") id: String): Response<CharacterDetailList>
}