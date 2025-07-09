package com.example.trasuatuananh.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.trasuatuananh.ui.main.MainActivity
import com.example.trasuatuananh.util.Constants


abstract class BaseDataBindActivity<T : ViewDataBinding, K> : BaseActivity(), AppBehaviorOnServiceError, BackClickHandler {
    protected var mBinding: T? = null
    protected var mPresenter: K? = null
    private val goHomeReceiver = object : LocalBroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                if (context !is MainActivity) {
                    finish()
                }
                MainActivity.self()?.let {
                    if (it.supportFragmentManager.backStackEntryCount > 0) {
                        it.backToRootStack()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val appBehaviorOnServiceError: AppBehaviorOnServiceError = NewUIAppBehaviorOnServiceError(this)

     fun getAppBehaviorOnServiceError(): AppBehaviorOnServiceError = appBehaviorOnServiceError

    override fun onCreate(savedInstanceState: Bundle?) {
        if (shouldProtectedCaptureScreen()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        initView()
        initData()

        OrderEnabledLocalBroadcastManager.getInstance(this).registerReceiver(
            goHomeReceiver,
            IntentFilter(Constants.Actions.NOTIFY_GO_HOME)
        )
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.x.toInt(), event.y.toInt())) {
                    v.clearFocus()
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onStart() {
        super.onStart()
        if (mPresenter is BasePresenter) {
            (mPresenter as BasePresenter).subscribe()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mPresenter is BasePresenter) {
            (mPresenter as BasePresenter).unSubscribe()
        }
    }

    override fun onDestroy() {
        if (shouldProtectedCaptureScreen()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }

        try {
            OrderEnabledLocalBroadcastManager.getInstance(this).unregisterReceiver(goHomeReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    protected open fun shouldProtectedCaptureScreen(): Boolean = false

    override fun handleServiceError(exception: BaseException) {
        appBehaviorOnServiceError.handleServiceError(exception)
    }

    override fun handleOnBackPress(): Boolean {
        return if (supportFragmentManager.backStackEntryCount > 0) {
            popBackStack()
            false
        } else {
            finish()
            true
        }
    }
}
