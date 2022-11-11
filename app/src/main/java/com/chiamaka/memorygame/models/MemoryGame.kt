package com.chiamaka.memorygame.models

import com.chiamaka.memorygame.utils.DEFAULT_ICONS


//this class
class MemoryGame (private val boardSize: BoardSize){

    //initializes carrier variable for the all the tiles created
    val cards: List<MemoryCard>
    //keeps count of pairs
    var numPairsFound = 0

    //creates a nullable vriable for a users'selected tile.
    //this allows comparisons with the position of a given tile in the method below
    private var indexOfSingleSelectedCard: Int? = null

    init {
        //initializes the board according to the size and count
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map {MemoryCard(it)}
    }

    //implement flipping action
    fun flipCard(position: Int): Boolean {
        val card = cards[position]
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            //call check for match function
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    //checks for match using the identifier
    private fun checkForMatch(position1: Int, position2: Int): Boolean{
        if (cards[position1].identifier != cards[position2].identifier){
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound ++
        return true
    }

    //flips the card to hide the image, when there is no match
    private fun restoreCards() {
        for (card in cards){
            if (!card.isMatched){
                card.isFaceUp = false
            }

        }
    }

    //checks for if game is completed
    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    //checks if a card is upwards
    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

}