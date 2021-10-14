package com.example.satriakurniawan.ui.characterdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.satriakurniawan.data.entities.CharacterDetailList
import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.data.repository.CharacterRepository
import com.example.satriakurniawan.utils.Resource

class CharacterDetailViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _character = _id.switchMap { id ->
        repository.getCharacter(id)
    }

    val character: LiveData<Resource<CharacterDetailList>> = _character

    fun start(id: String) {
        _id.value = id
    }

    fun catched(character: MyCharacter){
        repository.insertMyPokemon(character)
    }

}
