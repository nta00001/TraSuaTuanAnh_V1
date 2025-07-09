package com.example.trasuatuananh.widget.edittext.number

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object ClearableNumberInputBindings {
    @BindingAdapter("textInput")
    @JvmStatic
    fun setTextInput(view: ClearableNumberInput, value: String?) {
        if (view.text != value) {
            view.text = value ?: ""
        }
    }

    @InverseBindingAdapter(attribute = "textInput", event = "textInputAttrChanged")
    @JvmStatic
    fun getTextInput(view: ClearableNumberInput): String {
        return view.text
    }

    @BindingAdapter("textInputAttrChanged")
    @JvmStatic
    fun setTextInputListener(view: ClearableNumberInput, listener: InverseBindingListener?) {
        if (listener != null) {
            view.inputEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    listener.onChange()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}