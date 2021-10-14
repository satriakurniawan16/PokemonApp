package com.example.satriakurniawan.data.entities

data class CharacterDetailList(
    val stats: List<CharacterDetail>,
    val sprites: Sprites,
    val weight: Int,
    val name: String,
    val species: Species,
    val moves: List<Moves>,
    val types: List<Types>
)

data class Sprites(
    val front_default: String
)

data class Species(
    val name: String
)

data class Moves(
    val move: Move
)

data class Move(
    val name: String
)

data class Types(
    val type: Type
)
data class Type(
    val name: String
)