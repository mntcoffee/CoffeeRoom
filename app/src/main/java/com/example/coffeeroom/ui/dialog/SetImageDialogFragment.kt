package com.example.coffeeroom.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SetImageDialogFragment : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    private val photoSource = arrayOf("Take a photo", "Select a photo from your folder")

    // カメラから画像取得 or フォルダから画像取得
    interface NoticeDialogListener {
        fun onDialogCameraClick(dialog: DialogFragment)
        fun onDialogFolderClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            when {
                context is NoticeDialogListener -> listener = context
                parentFragment is NoticeDialogListener -> listener = parentFragment as NoticeDialogListener
            }
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(photoSource) { _, which ->
                when(which) {
                    0 -> {
                        listener.onDialogCameraClick(this)
                    }
                    else -> {
                        listener.onDialogFolderClick(this)
                    }
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
