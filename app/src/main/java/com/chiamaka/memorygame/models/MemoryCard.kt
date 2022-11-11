package com.chiamaka.memorygame.models

//holds the states of the tile.
//identifier parameter is interpreted as the position.
data class MemoryCard(
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched : Boolean = false
)