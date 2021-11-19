package com.example.tictactoemvvmtest.viewModel

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoemvvmtest.model.Cell
import com.example.tictactoemvvmtest.model.Game
import com.example.tictactoemvvmtest.model.Player
import com.example.tictactoemvvmtest.utilities.StringUtility.stringFromNumbers

class GameViewModel : ViewModel() {
    private var game: Game? = null
    var cells: ObservableArrayMap<String, String>? = null

    fun init(player1: String?, player2: String?) {
        game = Game(player1, player2)
        cells = ObservableArrayMap()
    }


    fun onClickedCellAt(row: Int, column: Int) {
        if (game!!.cells!![row][column] == null) {
            game!!.cells!![row][column] = Cell(game!!.currentPlayer)
            cells!![stringFromNumbers(row, column)] = game!!.currentPlayer!!.value
            if (game!!.hasGameEnded()) {
                game!!.reset()
            } else {
                game!!.switchPlayer()
            }
        }
    }

    fun getWinner(): LiveData<Player> {
        return game!!.winner
    }
}