package com.example.tictactoemvvmtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoemvvmtest.databinding.ActivityGameBinding
import com.example.tictactoemvvmtest.model.Player
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoemvvmtest.viewModel.GameViewModel

class GameActivity : AppCompatActivity() {
    private val GAME_BEGIN_DIALOG_TAG = "game_dialog_tag"
    private val GAME_END_DIALOG_TAG = "game_end_dialog_tag"
    private val NO_WINNER = "No one"
    private var gameViewModel: GameViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        promptForPlayers()
    }

    fun promptForPlayers() {
        val dialog: GameBeginDialog = GameBeginDialog().newInstance(this)
        dialog.show(supportFragmentManager, GAME_BEGIN_DIALOG_TAG)
    }

    fun onPlayersSet(player1: String, player2: String) {
        initDataBinding(player1, player2)
    }

    private fun initDataBinding(player1: String, player2: String) {
        val activityGameBinding: ActivityGameBinding = ActivityGameBinding.inflate(layoutInflater)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel?.init(player1, player2)
        activityGameBinding.gameViewModel = gameViewModel
        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        gameViewModel!!.getWinner().observe(this, this::onGameWinnerChanged)
    }

    @VisibleForTesting
    fun onGameWinnerChanged(winner: Player?) {
        val winnerName =
            winner?.name ?: NO_WINNER
        val dialog: GameEndDialog = GameEndDialog().newInstance(this, winnerName)
        dialog.show(supportFragmentManager, GAME_END_DIALOG_TAG)
    }
}