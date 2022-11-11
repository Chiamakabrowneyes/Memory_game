package com.chiamaka.memorygame

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.chiamaka.memorygame.models.BoardSize
import com.chiamaka.memorygame.models.MemoryCard
import kotlin.math.min

class MemoryBoardAdapter(
    private val context: MainActivity,
    private val boardSize: BoardSize,
    private val cards: List<MemoryCard>,
    private val cardClickListener: CardClickListener
) :

    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 10
        private const val TAG = "MemoryBoardAdapter"
    }

    interface CardClickListener {
        fun onCardClicked(position: Int)
    }
//onCreateViewHolder is called when you need a new View.
//If there is an available Recycled View that can be provided and be bound with new data, then onBindViewHolder is called
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //defines the dimensions for each tile, using the exact dimensions of the users' phone
        val cardWidth = parent.width/boardSize.getWidth() - ( 2* MARGIN_SIZE)
        val cardHeight = parent.height/boardSize.getHeight()- ( 2* MARGIN_SIZE)
        //use the minimum of both dimensions, since the tile is a square.
        val cardSideLength = min(cardWidth, cardHeight)

    //LayoutInflater is used to create a new View object from one of your memory_card.xml layouts.
        val view:View = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)
    //findviewbyid gives us a reference for teh already created view.
        val layoutParams = view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams

//assigns the width of the and height of the card view from the prior defination that is based on the dimension of the user phone

        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    //runs the bind function on the
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder. bind(position)
    }

    //returns the number of cards in the board
    override fun getItemCount() = boardSize.numCards

    //creating an inner class to logically group the class sets
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Create the image button for the tile from the memory_card.xml
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)


        fun bind(position: Int) {
            val memoryCard = cards[position]
            //reveals the card photo when it is set to faceup, and hides the card photo when it is not set to faceup
            imageButton.setImageResource(if (cards[position].isFaceUp) cards[position].identifier else R.drawable.ic_launcher_background)

            //turns the tiles grey when they are matched
            imageButton.alpha = if (memoryCard.isMatched) .4f else 1.0f
            val colorStateList = if (memoryCard.isMatched) ContextCompat.getColorStateList(context, R.color.color_gray) else null
            ViewCompat.setBackgroundTintList(imageButton, colorStateList)

            //monitors when the tile is clicked
            imageButton.setOnClickListener{
                Log.i(TAG, "Clicked on position $position")
                cardClickListener.onCardClicked((position))
            }
        }
    }

}
