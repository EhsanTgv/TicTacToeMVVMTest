package com.example.tictactoemvvmtest.model

data class Cell(var player: Player?) {
    val isEmpty: Boolean
        get() = player == null || player!!.value.isEmpty()
}