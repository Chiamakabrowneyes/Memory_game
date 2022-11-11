package com.chiamaka.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiamaka.memorygame.models.BoardSize
import com.chiamaka.memorygame.models.MemoryCard
import com.chiamaka.memorygame.models.MemoryGame
import com.chiamaka.memorygame.utils.DEFAULT_ICONS
import com.google.android.material.snackbar.Snackbar

//Main activity is the first screen to pop up when the application is opened.
//It contains the layouts that is defined in the activity_main.
class MainActivity : AppCompatActivity() {

    //companion object is used to define static variables, that is variables that remain unchanged through the program
    companion object{
        private const val TAG = "MainActivity"

    }

//These variables are components from the activity main.
    //lateinit: they are initialized with a lateinit their properties do not have values yet. This allows the complier to create assertions that make it impossible to read the its "values"
    //private: indicates that the components are only visible within the Mainactivity class
    private lateinit var clRoot: ConstraintLayout
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView

    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter

    //defines the difficulty level of the game
    private var boardSize: BoardSize = BoardSize.HARD


    //The oncreate bundle is called when the application starts. It is used for one time initializations
    //Here we observe the defination of the late-initialized variables by associating them with their IDs from the activity main.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        memoryGame = MemoryGame(boardSize)

        //defines the memory board adapter. This class deals with functioning of the board
        //which involves: creating the board, changing the state of the board when it is clicked or matched
        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener {

            //retrieves information for when the player has won, reached an invalid move or made a match.
            override fun onCardClicked(position:Int){
                Log.i(TAG, "Card clicked $position")

                updateGameWithFlip(position)
            }
        })

        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize((true))
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        if (memoryGame.haveWonGame()) {
            //snackbar is acomponent that displays a quick message for the user
            Snackbar.make(clRoot, "You already won", Snackbar.LENGTH_LONG).show()
            return
        }

        if (memoryGame.isCardFaceUp(position)){
            Snackbar.make(clRoot, "Invalid move!", Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.flipCard(position)) {
            Log.i(TAG, "found a match! Num pairs found: ${memoryGame.numPairsFound}")
        }

        //implements moves on the board eg flips, matches, etc
        adapter.notifyDataSetChanged()
    }

}