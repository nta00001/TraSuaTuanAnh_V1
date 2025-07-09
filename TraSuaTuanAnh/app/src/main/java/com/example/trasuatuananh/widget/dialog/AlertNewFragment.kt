package com.example.trasuatuananh.widget.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.databinding.FragmentShowMessageNewBinding


class AlertNewFragment(
    private val icon: Int?,
    private val title: String?,
    private val message: String?,
    private val textTopButton: String?,
    private val textBottomButton: String?,
    private val listener: AlertDialogListener? = null,
    private val cancelable: Boolean? = true,
    private val dismissListener: (() -> Unit)? = null,
    private var countDownTimer: CountDownTimer? = null,
    private val showCloseButton: Boolean = true
) : DialogFragment() {
    private val binding by lazy {
        FragmentShowMessageNewBinding.inflate(layoutInflater)
    }

    fun setCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
        }
        countDownTimer = object : CountDownTimer(Config.sectionTimeout.toLong(), 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                try {
                    dismissAllowingStateLoss()
                } catch (ignored: Exception) {
                }
            }
        }
        countDownTimer?.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = cancelable ?: true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            if ((icon ?: 0) > 0) {
                icon?.let { ivIconExpression.setImageResource(it) }
            }
            ivIconExpression.isVisible = (icon ?: 0) > 0
            tvTitle.isVisible = !TextUtils.isEmpty(title)
            if (!TextUtils.isEmpty(title)) {
                tvTitle.text = title
            }
            tvMessage.isGone = TextUtils.isEmpty(message)
            if (!TextUtils.isEmpty(message)) {
                message?.let {
                    tvMessage.text = it
                }
            }
            if (!TextUtils.isEmpty(textTopButton)) {
                tvTopButton.visibility = View.VISIBLE
                tvTopButton.text = textTopButton
            } else {
                tvTopButton.visibility = View.GONE
            }
            if (!TextUtils.isEmpty(textBottomButton)) {
                tvBottomButton.visibility = View.VISIBLE
                tvBottomButton.text = textBottomButton
            } else {
                tvBottomButton.visibility = View.GONE
                binding.view.visibility = View.VISIBLE
            }
            ivClose.isVisible = showCloseButton
            tvTopButton.setOnClickListener {
                listener?.onAccept()
                try {
                    dismissAllowingStateLoss()
                } catch (ignored: Exception) {
                }
            }
            tvBottomButton.setOnClickListener {
                listener?.onCancel()
                try {
                    dismissAllowingStateLoss()
                } catch (ignored: Exception) {
                }
            }
            ivClose.setOnClickListener {
                try {
                    dismissAllowingStateLoss()
                } catch (ignored: Exception) {
                }
            }
            view.clearFocus()
        }
        val window: Window? = dialog?.window
        val displayMetrics = resources.displayMetrics
        val width = (displayMetrics.widthPixels * 0.95).toInt()
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
        }
        super.onDestroy()
    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissListener?.invoke()
        super.onDismiss(dialog)
    }

    companion object {
        fun newInstance(
            icon: Int?,
            title: String?,
            message: String?,
            textTopButton: String?,
            textBottomButton: String?,
            isCancelable: Boolean?,
            showCloseButton: Boolean = true,
            listener: AlertDialogListener?,
            dismissListener: (() -> Unit)?,
        ): AlertNewFragment {
            return AlertNewFragment(
                icon,
                title,
                message,
                textTopButton,
                textBottomButton,
                showCloseButton = showCloseButton,
                listener = listener,
                cancelable = isCancelable,
                dismissListener = dismissListener,
            ).apply {
                setStyle(STYLE_NO_TITLE, R.style.TransparentDialog)
            }
        }
    }
}