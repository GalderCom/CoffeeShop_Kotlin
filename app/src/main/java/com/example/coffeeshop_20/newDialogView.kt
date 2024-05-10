package com.example.coffeeshop_20

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

@SuppressLint("UseCompatLoadingForDrawables")
class newDialogView(context: Context) : Dialog(context) {

    var positiveButton: TextView
    var negativeButton: TextView
    var text: TextView

    init {
        setContentView(R.layout.dialog_view)

        positiveButton = findViewById(R.id.btnOK)
        negativeButton = findViewById(R.id.btnCancel)

        text = findViewById(R.id.textDialog)

        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(context.getDrawable(R.drawable.transp))
    }



    fun setPositiveButtonClickListener(listener: View.OnClickListener) {
        positiveButton.setOnClickListener(listener)
    }

    fun setNegativeButtonClickListener(listener: View.OnClickListener) {
        negativeButton.setOnClickListener(listener)
    }
}