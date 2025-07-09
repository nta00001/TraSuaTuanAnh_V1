package com.example.trasuatuananh.widget.edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.trasuatuananh.R
import com.example.trasuatuananh.databinding.LayoutClearableTextInputBinding
import com.example.trasuatuananh.util.ScreenUtils
import com.example.trasuatuananh.util.SpecialCharacterFilter
import com.example.trasuatuananh.util.SpecialExceptSpaceCharacterFilter
import com.example.trasuatuananh.util.StringUtils
import com.example.trasuatuananh.util.restoreInstanceState
import com.example.trasuatuananh.util.saveInstanceState
import com.example.trasuatuananh.util.singleClick
import com.example.trasuatuananh.widget.listener.OnSingleClickListener


class ClearableTextInput : ConstraintLayout {

    lateinit var binding: LayoutClearableTextInputBinding

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

    var actionIcon: Drawable?
        set(value) {
            if (value == null) {
                binding.btnEditTextAction.visibility = View.GONE
                binding.btnEditTextAction.setImageDrawable(null)
                return
            }
            binding.btnEditTextAction.visibility = View.VISIBLE
            binding.btnEditTextAction.setImageDrawable(value)
        }
        get() = binding.btnEditTextAction.drawable

    private var hideClearText: Boolean = false

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
                it as LayoutInflater, R.layout.layout_clearable_text_input, this, true
            )
        }
    }

    private fun initView(attrs: AttributeSet) {
        try {
            context.obtainStyledAttributes(attrs, R.styleable.ClearableTextInput).run {
                title = getString(R.styleable.ClearableTextInput_labelTitle) ?: ""
                hint = getString(R.styleable.ClearableTextInput_hint) ?: ""
                actionIcon = getDrawable(R.styleable.ClearableTextInput_srcActionIcon)
                text = getString(R.styleable.ClearableTextInput_textInput) ?: ""
                hideClearText = getBoolean(R.styleable.ClearableTextInput_hideClearText, false)
                val limitInput = getInt(R.styleable.ClearableTextInput_limitInput, 0)
                val hideTitle = getBoolean(R.styleable.ClearableTextInput_hideTitle, false)
                val inputMaxLength =
                    getInt(R.styleable.ClearableTextInput_android_maxLength, Int.MAX_VALUE)
                val inputType =
                    getInt(R.styleable.ClearableTextInput_android_inputType, EditorInfo.TYPE_NULL)
                val enabledInput = getBoolean(R.styleable.ClearableTextInput_enabledInput, true)
                val titleStyle =
                    getResourceId(R.styleable.ClearableTextInput_titleStyle, 0)
                val titleTextColor =
                    getResourceId(R.styleable.ClearableTextInput_titleTextColor, 0)
                val textError = getString(R.styleable.ClearableTextInput_errorLabel)
                val enabledError = getBoolean(R.styleable.ClearableTextInput_enabledErrorLabel, false)
                setInputMaxLength(inputMaxLength)
                setInputType(inputType)
                setHideTitle(hideTitle)
                setLimitInput(limitInput)
                if (hideClearText) {
                    binding.btnClearText.visibility = View.GONE
                }
                if (!enabledInput) {
                    setEnableEditText(false)
                }
                binding.edtInput.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                    onFocusChangeListener?.invoke(hasFocus)
                }
                setTitleStyle(titleStyle)
                setTitleColor(titleTextColor)
                binding.edtInput.addTextChangedListener {
                    if (limitInput > 0) {
                        binding.tvLimitInput.text = context.getString(
                            R.string.text_limit_input_character,
                            it?.toString()?.length ?: 0,
                            limitInput
                        )
                    }

                    if (!hideClearText) {
                        binding.btnClearText.visibility =
                            if (binding.edtInput.text?.toString()
                                    .isNullOrEmpty()
                            ) View.GONE else View.VISIBLE
                    }
                }
                binding.btnClearText.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        onClearTextListener?.invoke()
                        binding.edtInput.setText("")
                    }
                })
                recycle()
            }
        } catch (_: Exception) {
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        post {
            updatePadding()
        }
    }

    private fun updatePadding() {
        binding.edtInput.updatePadding(
            right = binding.llAction.width + ScreenUtils.dpToPx(
                context,
                4
            )
        )
        invalidate()
    }

    fun setOnActionClickListener(listener: OnClickListener) {
        binding.btnEditTextAction.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                listener.onClick(v)
            }
        })
    }

    fun setHideClearText(isHide: Boolean) {
        hideClearText = isHide
        binding.btnClearText.visibility = if (hideClearText || text.isEmpty()) View.GONE else View.VISIBLE
    }

    fun setEnableEditText(isEnabled: Boolean = true) {
        inputEditText.isLongClickable = isEnabled
        inputEditText.isFocusable = isEnabled
        inputEditText.setEnabled(isEnabled)
        inputEditText.setCursorVisible(isEnabled)
        binding.layoutClick.visibility = if(isEnabled) View.GONE else View.VISIBLE
    }

    fun setEnableEditTextClickable(isEnabled: Boolean) {
        binding.layoutClick.visibility = if (isEnabled) View.VISIBLE else View.GONE
        setTitleColor(if (isEnabled) R.color.text_subtle else R.color.black_alpha_text_24)
        setTextColor(if (isEnabled) R.color.text_normal else R.color.black_alpha_text_24)
        binding.btnEditTextAction.imageTintList = ContextCompat.getColorStateList(
            context,
            if (isEnabled) R.color.text_normal else R.color.black_alpha_text_24
        )
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

    fun hideLimitInput(isHideLimitInput: Boolean) {
        binding.tvLimitInput.visibility = if (isHideLimitInput) View.GONE else View.VISIBLE
    }

    fun setInputBackgroundResource(@DrawableRes drawableId: Int) {
        inputEditText.setBackgroundResource(drawableId)
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

    fun setNumberInputType() {
        inputEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        inputEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
    }

    fun setFilterBlockCharacterExceptSpace() {
        setInputFilterEditText(SpecialExceptSpaceCharacterFilter())
    }

    fun setFilterBlockCharacter() {
        setInputFilterEditText(SpecialCharacterFilter())
    }

    fun setTextAllCaps() {
        setInputFilterEditText(InputFilter.AllCaps())
    }

    fun setSelection(index: Int) {
        inputEditText.setSelection(index)
    }

    fun setInputFilterEditText(inputFilter: InputFilter) {
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

    fun setOnEditTextClickListener(listener: () -> Unit) {
        binding.layoutClick.singleClick { listener.invoke() }
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        inputEditText.isEnabled = isEnabled
        binding.btnEditTextAction.isEnabled = isEnabled
        binding.btnClearText.isEnabled = isEnabled
    }

    fun setTransformationMethod(method: TransformationMethod?) {
        inputEditText.transformationMethod = method
    }

    override fun onSaveInstanceState(): Parcelable? {
        return saveInstanceState(super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(restoreInstanceState(state))
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    fun setTitleStyle(style: Int) {
        if (style == 0) return
        TextViewCompat.setTextAppearance(binding.tvTitle, style)
    }

    fun setTitleColor(color: Int) {
        if (color == 0) return
        binding.tvTitle.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setTextColor(color: Int) {
        if (color == 0) return
        binding.edtInput.setTextColor(ContextCompat.getColor(context, color))
    }
}