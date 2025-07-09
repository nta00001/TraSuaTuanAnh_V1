package com.example.trasuatuananh.base

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.example.trasuatuananh.widget.dialog.AlertDialogListener

abstract class BaseDialogFragment : DialogFragment(), BaseScreen {

    private var isFullScreen = false

    protected abstract fun getStyleDialog(): Int

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), getStyleDialog())
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val window = dialog?.window
        window?.let {
            if (isFullScreen) {
                it.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                it.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        hideLoadingDialog()
    }

    override fun showMessage(message: String) {
        (activity as? BaseActivity)?.showMessage(message)
    }

    fun getBaseActivity(): BaseActivity? {
        return activity as? BaseActivity
    }

    protected fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun setFullScreen(fullScreen: Boolean) {
        isFullScreen = fullScreen
    }

    fun showLoadingDialog() {
        showLoadingDialog(true)
    }

    private fun hideLoadingDialog() {
        (activity as? BaseActivity)?.hideLoadingDialog()
    }

    fun showLoadingDialog(cancelable: Boolean) {
        (activity as? BaseActivity)?.showLoadingDialog()
    }

    fun goToDialogFragment(mBaseDialog: BaseDialogFragment, mBundle: Bundle) {
        (activity as? BaseActivity)?.goToDialogFragment(mBaseDialog, mBundle)
    }

    fun goToFragment(
        @IdRes fragmentContainerId: Int,
        mBaseFragment: BaseFragment,
        mBundle: Bundle
    ) {
        (activity as? BaseActivity)?.addFragment(fragmentContainerId, mBaseFragment, mBundle)
    }

    fun onBackFragment() {
        (activity as? BaseActivity)?.onBackFragment()
    }

    fun openActivityForResult(clazz: Class<*>, bundle: Bundle?, requestCode: Int) {
        (activity as? BaseActivity)?.openActivityForResult(clazz, bundle, requestCode)
    }

    fun openActivity(clazz: Class<*>) {
        openActivity(clazz, null)
    }

    fun openActivity(clazz: Class<*>, isFinish: Boolean) {
        (activity as? BaseActivity)?.openActivity(clazz, isFinish)
    }

    fun openActivity(clazz: Class<*>, bundle: Bundle?) {
        (activity as? BaseActivity)?.openActivity(clazz, bundle)
    }

    fun showAlertDialog(
        @StringRes title: Int, @StringRes message: Int,
        cancellable: Boolean,
        listener: AlertDialogListener
    ) {
        showAlertDialog(getString(title), getString(message), cancellable, listener)
    }

    fun showAlertDialog(
        title: String, message: String,
        cancellable: Boolean,
        listener: AlertDialogListener
    ) {
        (activity as? BaseActivity)?.showAlertDialog(title, message, cancellable, listener)
    }


    fun showShortToast(@StringRes message: Int) {
        showShortToast(getString(message))
    }

    fun showLongToast(@StringRes message: Int) {
        showLongToast(getString(message))
    }

    private fun showShortToast(message: String) {
        (activity as? BaseActivity)?.showShortToast(message)
    }

    private fun showLongToast(message: String) {
        (activity as? BaseActivity)?.showLongToast(message)
    }

}

