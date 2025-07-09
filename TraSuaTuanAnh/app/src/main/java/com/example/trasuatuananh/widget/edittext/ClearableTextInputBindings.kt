package com.example.trasuatuananh.widget.edittext

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object ClearableTextInputBindings {
    @BindingAdapter("textInput")
    @JvmStatic
    fun setTextInput(view: ClearableTextInput, value: String?) {
        if (view.text != value) {
            view.text = value ?: ""
        }
    }

    @BindingAdapter("textResource")
    @JvmStatic
    fun setTextInput(view: ClearableTextInput, @StringRes value: Int?) {
        value?.let {
            if (it == 0) {
                view.text = ""
                return
            }
            view.text = view.context.getString(value)
        }
    }

    @InverseBindingAdapter(attribute = "textInput", event = "textInputAttrChanged")
    @JvmStatic
    fun getTextInput(view: ClearableTextInput): String {
        return view.text
    }

    @BindingAdapter("textInputAttrChanged")
    @JvmStatic
    fun setTextInputListener(view: ClearableTextInput, listener: InverseBindingListener?) {
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