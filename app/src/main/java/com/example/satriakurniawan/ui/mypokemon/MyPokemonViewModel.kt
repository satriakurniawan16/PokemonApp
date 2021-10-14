package com.example.satriakurniawan.ui.mypokemon

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.data.repository.CharacterRepository

class MyPokemonViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters = repository.getMyCharacters()

    fun update(character: MyCharacter){
        repository.updateMyPokemon(character)
    }
}