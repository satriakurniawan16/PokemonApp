package com.example.satriakurniawan.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.satriakurniawan.data.entities.Character
import com.example.satriakurniawan.data.entities.MyCharacter

@Dao
interface CharacterDao {

    @Query("SELECT * FROM pokemon")
    fun getAllCharacters() : LiveData<List<Character>>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getCharacter(id: Int): LiveData<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("SELECT * FROM mypokemon")
    fun getCatchedPokemon() : LiveData<List<MyCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyPokemon(pokemon: MyCharacter)


    @Update
    fun updateMyPokemon(pokemon: MyCharacter)

}