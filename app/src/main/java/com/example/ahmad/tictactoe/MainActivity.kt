package com.example.ahmad.tictactoe

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity()
{
    var activePlayer = 1
    var grid = ArrayList<ArrayList<Int>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for(i in 0..2)
        {
            var row = ArrayList<Int>()
            for(j in 0..2)
                row.add(0)
            grid.add(row)
        }
    }
    @SuppressLint("ResourceAsColor")
    fun buttonSelect(view : View)
    {
        val buttonChoice = view as Button
        var cellId = -1
        when(buttonChoice.id)
        {
            R.id.button1->cellId=0
            R.id.button2->cellId=1
            R.id.button3->cellId=2
            R.id.button4->cellId=3
            R.id.button5->cellId=4
            R.id.button6->cellId=5
            R.id.button7->cellId=6
            R.id.button8->cellId=7
            R.id.button9->cellId=8
        }
        Log.d("Cell ID : ",cellId.toString())
        playGame(cellId,buttonChoice)
    }

    fun playGame(cellId:Int, buttonChoice : Button)
    {
        if(!buttonChoice.text.isEmpty())
        {
            Toast.makeText(this,"This place is taken",Toast.LENGTH_SHORT).show()
            return
        }
        var rowIndex = cellId/3
        var columnIndex = cellId%3
        grid[rowIndex][columnIndex]=activePlayer
        if(activePlayer == 1)
        {
            buttonChoice.text = "X"
            buttonChoice.setBackgroundColor(resources.getColor(R.color.blue))
        }
        else
        {
            buttonChoice.text = "O"
            buttonChoice.setBackgroundColor(resources.getColor(R.color.darkgreen))
        }
        val winner = checkWinner()
        if(winner!=-1)
        {
            showWinnerDialog(winner)
        }
        else if(Draw())
        {
            showDrawDialog()
        }
        activePlayer=3-activePlayer    // if 2 then active = 1   -- if 1 then active = 2
    }
    fun showWinnerDialog(winner : Int)
    {
        val builder =AlertDialog.Builder(this)
        builder.setMessage("Player $winner has won the game")
        builder.setPositiveButton("Play Again",DialogInterface.OnClickListener{dialog,which->reset()})
        builder.setNegativeButton("Exit",DialogInterface.OnClickListener{dialog, which -> finish() })
        builder.setOnDismissListener(DialogInterface.OnDismissListener { Toast.makeText(this,"Game has finished",Toast.LENGTH_SHORT).show() })
        builder.show()
    }
    fun showDrawDialog()
    {
        val builder =AlertDialog.Builder(this)
        builder.setMessage("Game is Drawn")
        builder.setPositiveButton("Play Again",DialogInterface.OnClickListener{dialog,which->reset()})
        builder.setNegativeButton("Exit",DialogInterface.OnClickListener{dialog, which -> finish() })

        builder.show()
    }
    fun reset()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Draw() : Boolean
    {
        for(i in 0..2)
            for(j in 0..2)
                if(grid[i][j]==0)
                    return false
        return true
    }
    fun checkWinner() : Int
    {
        val rows=checkRows()
        val columns=checkColumns()
        val diagonals=checkDiagonals()
        if(rows!=-1)
            return rows
        if(columns!=-1)
            return columns
        if(diagonals!=-1)
            return diagonals
        return -1
    }
    fun checkRows() : Int
    {
        for(i in 0..2)
            if(grid[i][0]==grid[i][1]&&grid[i][1]==grid[i][2] && grid[i][0]!=0)
                return grid[i][0]
        return -1
    }
    fun checkColumns() : Int
    {
        for(i in 0..2)
            if(grid[0][i]==grid[1][i]&&grid[1][i]==grid[2][i] && grid[0][i]!=0)
                return grid[0][i]
        return -1
    }

    fun checkDiagonals() : Int
    {
        if(grid[0][0] == grid[1][1] && grid[1][1]==grid[2][2] && grid[2][2]!=0)
            return grid[1][1]
        if(grid[0][2] == grid[1][1] && grid[1][1]==grid[2][0] && grid[0][2]!=0)
            return grid[1][1]
        return -1
    }

}
