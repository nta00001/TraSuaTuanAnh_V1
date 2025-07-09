package com.example.trasuatuananh.widget.edittext.number

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.trasuatuananh.R
import com.example.trasuatuananh.databinding.LayoutClearableNumberInputBinding
import com.example.trasuatuananh.util.ScreenUtils
import com.example.trasuatuananh.util.StringUtils
import com.example.trasuatuananh.widget.listener.OnSingleClickListener


class ClearableNumberInput : ConstraintLayout {

    lateinit var binding: LayoutClearableNumberInputBinding

    var onClearTextListener: (() -> Unit)? = null
    var onFocusChangeListener: ((hasFocus: Boolean) -> Unit)? = null

    var oldText = ""

    val inputEditText get() = binding.edtInput

    var title: String
        set(value) {
            binding.tvTitle.text = value
        }
        get() = binding.tvTitle.text?.toString() ?: ""

    var hint: String
        set(value) {
            binding.edtInput.hint = value
        }
        get() = binding.edtInput.hint.toString()

    var text: String
        set(value) {
            binding.edtInput.setText(value)
            invalidate()
        }
        get() = binding.edtInput.text.toString()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initBinding()
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initBinding()
        initView(attrs)
    }

    private fun initBinding() {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).let {
            binding = DataBindingUtil.inflate(
                it as LayoutInflater, R.layout.layout_clearable_number_input, this, true
            )
        }
    }

    private fun initView(attrs: AttributeSet) {
        try {
            context.obtainStyledAttributes(attrs, R.styleable.ClearableAmountInput).run {
                title = getString(R.styleable.ClearableAmountInput_labelTitle) ?: ""
                hint = getString(R.styleable.ClearableAmountInput_hint) ?: ""
                text = getString(R.styleable.ClearableAmountInput_textInput) ?: ""
                val hideClearText =
                    getBoolean(R.styleable.ClearableAmountInput_hideClearText, false)
                val limitInput = getInt(R.styleable.ClearableAmountInput_limitInput, 0)
                val hideTitle = getBoolean(R.styleable.ClearableAmountInput_hideTitle, false)
                val inputMaxLength =
                    getInt(R.styleable.ClearableAmountInput_android_maxLength, Int.MAX_VALUE)
                val inputType =
                    getInt(R.styleable.ClearableAmountInput_android_inputType, EditorInfo.TYPE_NULL)
                val enabledInput = getBoolean(R.styleable.ClearableAmountInput_enabledInput, true)
                setInputMaxLength(inputMaxLength)
                setInputType(inputType)
                setHideTitle(hideTitle)
                setLimitInput(limitInput)
                if (hideClearText) {
                    binding.btnClearTextAmount.visibility = View.GONE
                }
                if (!enabledInput) {
                    setDisableEditText()
                }
                binding.edtInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                    onFocusChangeListener?.invoke(hasFocus)
                }
                binding.edtInput.addTextChangedListener {
                    if (limitInput > 0) {
                        binding.tvLimitInput.text = context.getString(
                            R.string.text_limit_input_character,
                            it?.toString()?.length ?: 0,
                            limitInput
                        )
                    }

                    if (!hideClearText) {
                        binding.btnClearTextAmount.visibility =
                            if (binding.edtInput.text?.toString()
                                    .isNullOrEmpty()
                            ) View.GONE else View.VISIBLE
                    }
                }
                binding.btnClearTextAmount.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        onClearTextListener?.invoke()
                        binding.edtInput.setText("")
                    }
                })
                recycle()
                requestLayout()
            }
        } catch (_: Exception) {
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        binding.edtInput.updatePadding(
            right = binding.llAction.width + ScreenUtils.dpToPx(
                context,
                4
            )
        )
    }


    fun setDisableEditText() {
        inputEditText.isLongClickable = false
        inputEditText.isFocusable = false
        inputEditText.setEnabled(false)
        inputEditText.setCursorVisible(false)
        inputEditText.setKeyListener(null)
    }

    fun setInputMaxLength(length: Int) {
        setInputFilterEditText(InputFilter.LengthFilter(length))
    }

    fun setInputType(inputType: Int) {
        if (inputType == EditorInfo.TYPE_NULL) return
        binding.edtInput.inputType = inputType
    }

    fun setHideTitle(hide: Boolean) {
        binding.tvTitle.visibility = if (hide) View.GONE else View.VISIBLE
    }

    fun setLimitInput(limitInput: Int) {
        if (limitInput == 0) {
            binding.tvLimitInput.visibility = View.GONE
            binding.tvLimitInput.text = null
            return
        }
        binding.tvLimitInput.visibility = View.VISIBLE
        binding.tvLimitInput.text = context.getString(
            R.string.text_limit_input_character,
            0,
            limitInput
        )
        setInputMaxLength(limitInput)
    }

    fun setInputBackgroundResource(@DrawableRes drawableId: Int) {
        inputEditText.setBackgroundResource(drawableId)
    }

    fun setTextInputType(textInputType: Boolean) {
        if (textInputType) {
            setTextNonSpecialCharacters()
        } else {
            inputEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        }
    }

    fun setTextNonSpecialCharacters() {
        inputEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        inputEditText.doAfterTextChanged {
            oldText = StringUtils.removeEdittextInputAccent(
                inputEditText,
                it?.toString(),
                oldText
            )
        }
    }


    fun setTextAllCaps() {
        setInputFilterEditText(InputFilter.AllCaps())
    }

    private fun setInputFilterEditText(inputFilter: InputFilter) {
        val editFilters: Array<InputFilter> = inputEditText.filters
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = inputFilter
        inputEditText.setFilters(newFilters)
    }

    fun addOnFocusChangeListener(listener: (hasFocus: Boolean) -> Unit) {
        onFocusChangeListener = listener
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        inputEditText.addTextChangedListener(watcher)
    }

    inline fun doAfterTextChanged(
        crossinline action: (text: Editable?) -> Unit
    ): TextWatcher = inputEditText.addTextChangedListener(afterTextChanged = action)

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        inputEditText.setOnEditorActionListener(listener)
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        inputEditText.isEnabled = isEnabled
        binding.btnClearTextAmount.isEnabled = isEnabled
    }

    fun setTransformationMethod(method: TransformationMethod?) {
        inputEditText.transformationMethod = method
    }


}







