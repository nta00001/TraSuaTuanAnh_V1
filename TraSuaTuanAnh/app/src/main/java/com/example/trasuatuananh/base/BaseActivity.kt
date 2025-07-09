package com.example.trasuatuananh.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.example.trasuatuananh.R
import com.example.trasuatuananh.ui.home.homegroup.HomeGroupFragment
import com.example.trasuatuananh.util.ActivityUtils
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.LifeCycleExt.launchWhenResumed
import com.example.trasuatuananh.widget.dialog.AlertDialogListener
import com.example.trasuatuananh.widget.dialog.AlertNewFragment
import com.example.trasuatuananh.widget.loading.LoadingDialog

abstract class BaseActivity : AppCompatActivity(), BaseScreen, ScreenNavigator {
    private val mLoadingDialog by lazy {
        LoadingDialog.newInstance().apply {
            isCancelable = false
        }
    }
    protected val handler = Handler(Looper.getMainLooper())
    private var alertNewFragment: AlertNewFragment? = null
    override fun showLoading() {
        val showLoadingIntent = Intent(Constants.Actions.NOTIFY_SHOW_LOADING)
        OrderEnabledLocalBroadcastManager.getInstance(this).sendBroadcast(showLoadingIntent)
    }

    override fun hideLoading() {
        val showLoadingIntent = Intent(Constants.Actions.NOTIFY_HIDE_LOADING)
        OrderEnabledLocalBroadcastManager.getInstance(this).sendBroadcast(showLoadingIntent)
    }

    override fun showMessage(message: String) {
        showAlertDialog(message)
    }

    fun showLoadingDialog() {
        handler.post {
            try {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    if (isFinishing)
                        return@post
                    if (!mLoadingDialog.isAdded)
                        mLoadingDialog.showNow(
                            supportFragmentManager,
                            LoadingDialog::class.java.name
                        )
                    supportFragmentManager.executePendingTransactions()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideLoadingDialog() {
        handler.post {
            try {
                if (isFinishing)
                    return@post
                if (mLoadingDialog.isAdded)
                    mLoadingDialog.dismissAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            getString(title),
            getString(message),
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes acceptLabel: Int,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            title = getString(title),
            message = getString(message),
            textTopButton = getString(acceptLabel),
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes acceptLabel: Int,
        @StringRes cancelLabel: Int?,
        cancelable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            getString(title),
            getString(message),
            getString(acceptLabel),
            if (cancelLabel != null) getString(cancelLabel) else null,
            isCancelable = cancelable,
            listener = listener
        )
    }

    fun showAlertDialog(
        message: String?,
        @StringRes acceptLabel: Int,
        @StringRes cancelLabel: Int?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            "",
            message = message,
            textTopButton = getString(acceptLabel),
            textBottomButton = if (cancelLabel != null) getString(cancelLabel) else null,
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(message: String?) {
        showAlertDialogNew(-1, message = message)
    }

    fun showAlertDialog(message: String?, listener: AlertDialogListener?) {
        showAlertDialogNew(-1, message = message, listener = listener)
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        cancelable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            title = title,
            message = message,
            listener = listener,
            isCancelable = cancelable
        )
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        btnOke: String?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            title = title,
            message = message,
            textTopButton = btnOke,
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(message: String?, acceptLabel: String?, listener: AlertDialogListener?) {
        showAlertDialogNew(
            -1,
            message = message,
            textTopButton = acceptLabel,
            listener = listener
        )
    }

    fun showAlertSuccessDialog(
        message: String?,
        acceptLabel: String?,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            R.drawable.ic_success_new,
            message = message,
            textTopButton = acceptLabel,
            listener = listener
        )
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        acceptLabel: String?,
        cancelLabel: String?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            title,
            message,
            acceptLabel,
            cancelLabel,
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        span: String?,
        acceptLabel: String?,
        cancelLabel: String?,
        cancellable: Boolean,
        autoCancel: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialogNew(
            -1,
            title,
            message,
            acceptLabel,
            cancelLabel,
            autoCancel = autoCancel,
            isCancelable = cancellable,
            listener = listener
        )
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        span: String?,
        acceptLabel: String?,
        cancelLabel: String?,
        cancellable: Boolean,
        listenerSpan: AlertDialogSpanListener?
    ) {
        showAlertDialog(
            title,
            message,
            span,
            acceptLabel,
            cancelLabel,
            cancellable,
            false,
            listenerSpan
        )
    }

    @JvmOverloads
    fun showAlertDialogNew(
        icon: Int? = -1,
        title: String? = null,
        message: String? = null,
        textTopButton: String? = getString(R.string.common_close),
        textBottomButton: String? = null,
        autoCancel: Boolean = false,
        listener: AlertDialogListener? = null,
        dismissListener: (() -> Unit)? = null,
        isCancelable: Boolean = true,
        showCloseButton: Boolean = isCancelable
    ) {
        try {
            if (isFinishing) {
                return
            }
            dismissAlertNewDialog()
            alertNewFragment =
                AlertNewFragment.newInstance(
                    icon, title, message, textTopButton, textBottomButton,
                    isCancelable,
                    showCloseButton = showCloseButton,
                    listener,
                    dismissListener
                ).apply {
                    if (autoCancel) {
                        setCountDownTimer()
                    }
                }

            addAlertNewDialog(alertNewFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun popBackStack(flags: Int) {
        popBackStack(null, flags)
    }

    @JvmOverloads
    fun popBackStack(name: String? = null, flags: Int = 0) {
        popBackStack(supportFragmentManager, name, flags)
    }

    fun popBackStack(fragmentManager: FragmentManager, name: String?, flags: Int) {
        this.launchWhenResumed {
            try {
                fragmentManager.popBackStackImmediate(name, flags)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun goToDialogFragment(mBaseDialog: BaseDialogFragment, mBundle: Bundle?) {
        val fragmentManager = supportFragmentManager
        mBaseDialog.show(fragmentManager, mBaseDialog.javaClass.name)
    }

    @JvmOverloads
    fun addFragment(
        @IdRes fragmentContainerId: Int,
        mBaseFragment: BaseFragment,
        mBundle: Bundle? = bundleOf(),
        addToBackStack: Boolean = true
    ) {
        mBaseFragment.arguments = mBundle
        addFragment(mBaseFragment, fragmentContainerId, addToBackStack)
    }

    fun addFragment(
        fragment: Fragment,
        @IdRes containerViewId: Int,
        addToBackStack: Boolean = true
    ) {
        supportFragmentManager.beginTransaction()
            .add(containerViewId, fragment, fragment.javaClass.name)
            .apply {
                if (addToBackStack)
                    addToBackStack(fragment.javaClass.name)
            }.commitAllowingStateLoss()
    }

    fun onBackFragment() {
        popBackStack()

        ActivityUtils.hideSoftInput(this)
    }

    @JvmOverloads
    fun onBackAllFragments(closeDialog: Boolean = true) {
        if (closeDialog) dismissAlertNewDialog()

        // Quay về HomeGroupFragment và xóa tất cả các fragment phía trên nó
        supportFragmentManager.popBackStack(
            HomeGroupFragment::class.java.name,
            0
        )

        ActivityUtils.hideSoftInput(this)
    }


    fun openActivityForResult(clazz: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    fun openActivity(clazz: Class<*>?, isFinish: Boolean) {
        openActivity(clazz)
        if (isFinish) {
            finish()
        }
    }

    @JvmOverloads
    fun openActivity(clazz: Class<*>?, bundle: Bundle? = null) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    fun openActivityForResult(
        resultLauncher: ActivityResultLauncher<Intent>, clazz: Class<*>?, bundle: Bundle?
    ) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        resultLauncher.launch(intent)
    }


    fun showShortToast(@StringRes message: Int) {
        showShortToast(getString(message))
    }

    fun showLongToast(@StringRes message: Int) {
        showLongToast(getString(message))
    }

    fun showShortToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun removeAndReplaceFragment(fragment: Fragment, @IdRes containerViewId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(containerViewId)
        val transaction = supportFragmentManager.beginTransaction()
        if (currentFragment != null) transaction.remove(currentFragment)
        transaction.replace(containerViewId, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commitAllowingStateLoss()
    }

    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) removeFragment(supportFragmentManager, fragment)
    }

    fun removeFragment(tag: String?) {
        removeFragment(supportFragmentManager, tag)
    }

    fun removeFragment(fragmentManager: FragmentManager, tag: String?) {
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) removeFragment(fragmentManager, fragment)
    }

    fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment?) {
        fragment?.let { fragmentManager.beginTransaction().remove(it).commit() }
    }

    @JvmOverloads
    fun replaceFragment(
        fragment: Fragment,
        @IdRes containerViewId: Int,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.name)
        transaction.replace(containerViewId, fragment, fragment.javaClass.name)
            .commitAllowingStateLoss()
    }


    private fun dismissAlertNewDialog() {
        try {
            alertNewFragment?.dismissAllowingStateLoss()
            alertNewFragment = null
        } catch (ignored: Exception) {
        }
    }

    private fun addAlertNewDialog(alertNewFragment: AlertNewFragment?) {
        this.alertNewFragment = alertNewFragment
        if (alertNewFragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(alertNewFragment, AlertNewFragment::class.java.name)
            ft.commitAllowingStateLoss()
        }
    }

    override fun backToRootStack() {
        popBackStack(FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}