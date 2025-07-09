package com.example.trasuatuananh.widget.loading

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.trasuatuananh.R
import androidx.annotation.NonNull

class LoadingDialog : DialogFragment() {

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDimAmount(0f)
        }
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
        return dialog
    }
}
