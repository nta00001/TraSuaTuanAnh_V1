package com.example.trasuatuananh.widget.toolbar

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.trasuatuananh.R
import com.example.trasuatuananh.databinding.LayoutCommonToolbarBinding

class CommonToolbar : ConstraintLayout {

    private var binding: LayoutCommonToolbarBinding? = null
    
    constructor(context: Context) : super(context) {
        initUI(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initUI(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initUI(context, attrs, defStyleAttr)
    }

    fun setOnBackClickListener(listener: OnClickListener?) {
        binding?.flBack?.setOnClickListener(listener)
    }

    fun setOnRightClickListener(listener: OnClickListener?) {
        binding?.flRight?.setOnClickListener(listener)
    }

    fun setTitle(title: String?) {
        binding?.tvTitle?.text = title
    }

    fun hideBackIcon(hide: Boolean) {
        binding?.flBack?.isVisible = !hide
    }

    fun invisibleBackIcon(invisible: Boolean) {
        binding?.flBack?.isInvisible = invisible
        binding?.flBack?.isEnabled = !invisible
    }

    fun hiddenRightIcon(hidden: Boolean) {
        binding?.flRight?.isVisible = !hidden
    }

    fun showRightIcon(show: Boolean) {
        binding?.flRight?.isInvisible = !show
        binding?.flRight?.isEnabled = show
    }

    fun setRightIcon(drawable: Drawable?) {
        binding?.ivRight?.setImageDrawable(drawable)
    }

    fun setRightColorFilter(color: Int) {
        binding?.ivRight?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    fun setRightPadding(padding: Int) {
        binding?.ivRight?.setPadding(padding, padding, padding, padding)
    }

    fun setBgColor(idColor: Int) {
        binding?.root?.setBackgroundColor(ContextCompat.getColor(context, idColor))
    }

    fun setColorIvBack(idColor: Int) {
        binding?.ivBack?.imageTintList =
            ContextCompat.getColorStateList(context, idColor)
    }

    fun setColorTitle(idColor: Int) {
        binding?.tvTitle?.setTextColor(ContextCompat.getColor(context, idColor))
    }

    private fun initUI(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LayoutCommonToolbarBinding.inflate(inflater, this, true)
        
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CommonToolbar, 0, 0)
        try {
            val title = typedArray.getString(R.styleable.CommonToolbar_title)
            val titleColor = typedArray.getColor(R.styleable.CommonToolbar_titleColor, Color.WHITE)
            binding?.tvTitle?.text = title
            binding?.tvTitle?.setTextColor(titleColor)
        } finally {
            typedArray.recycle()
        }
    }
}