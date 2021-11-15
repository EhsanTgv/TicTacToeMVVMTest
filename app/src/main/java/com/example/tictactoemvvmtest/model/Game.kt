package com.example.tictactoemvvmtest.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.lang.NullPointerException

class Game(playerOne: String?, playerTwo: String?) {
    var player1: Player? = Player(playerOne!!, "x")
    var player2: Player? = Player(playerTwo!!, "o")
    var currentPlayer = player1
    var cells: Array<Array<Cell?>>? = Array(BOARD_SIZE) {
        arrayOfNulls(
            BOARD_SIZE
        )
    }
    var winner = MutableLiveData<Player>()
    fun switchPlayer() {
        currentPlayer = if (currentPlayer === player1) player2 else player1
    }

    companion object {
        private val TAG = Game::class.java.simpleName
        private const val BOARD_SIZE = 3
    }

    init {
        currentPlayer = player1
    }

    fun hasGameEnded(): Boolean {
        if (hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()) {
            winner.value = currentPlayer
            return true
        }
        if (isBoardFull()) {
            winner.value = null
            return true
        }
        return false
    }

    fun hasThreeSameHorizontalCells(): Boolean {
        return try {
            for (i in 0 until BOARD_SIZE) if (areEqual(
                    cells!![i][0]!!,
                    cells!![i][1]!!, cells!![i][2]!!
                )
            ) return true
            false
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message ?: "")
            false
        }
    }

    fun hasThreeSameVerticalCells(): Boolean {
        return try {
            for (i in 0 until BOARD_SIZE) if (areEqual(
                    cells!![0][i]!!,
                    cells!![1][i]!!, cells!![2][i]!!
                )
            ) return true
            false
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message ?: "")
            false
        }
    }

    fun hasThreeSameDiagonalCells(): Boolean {
        return try {
            areEqual(cells!![0][0]!!, cells!![1][1]!!, cells!![2][2]!!) ||
                    areEqual(cells!![0][2]!!, cells!![1][1]!!, cells!![2][0]!!)
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message ?: "")
            false
        }
    }

    fun isBoardFull(): Boolean {
        for (row in cells!!) for (cell in row) if (cell == null || cell.isEmpty) return false
        return true
    }

    private fun areEqual(vararg cells: Cell): Boolean {
        if (cells.isEmpty()) return false
        for (cell in cells) if (cell.player!!.value.isEmpty()) return false
        val (player) = cells[0]
        for (i in 1 until cells.size) if (player!!.value != cells[i].player!!.value) return false
        return true
    }

    fun reset() {
        player1 = null
        player2 = null
        currentPlayer = null
        cells = null
    }
}