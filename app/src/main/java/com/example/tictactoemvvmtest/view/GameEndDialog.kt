package com.example.tictactoemvvmtest.view

import androidx.fragment.app.DialogFragment
import android.widget.TextView
import android.view.LayoutInflater
import android.os.Bundle
import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.tictactoemvvmtest.R


class GameEndDialog : DialogFragment() {

    private var rootView: View? = null
    private var activity: GameActivity? = null
    private var winnerName: String? = null

    fun newInstance(activity: GameActivity, winnerName: String?): GameEndDialog {
        val dialog = GameEndDialog()
        dialog.activity = activity
        dialog.winnerName = winnerName
        return dialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViews()
        val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setView(rootView)
            .setCancelable(false)
            .setPositiveButton(R.string.done) { dialog, which -> onNewGame() }
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        return alertDialog
    }

    private fun initViews() {
        rootView = LayoutInflater.from(context)
            .inflate(R.layout.game_end_dialog, null, false)
        (rootView!!.findViewById<View>(R.id.tv_winner) as TextView).text = winnerName
    }

    private fun onNewGame() {
        dismiss()
        (requireActivity() as GameActivity).promptForPlayers()
    }
}