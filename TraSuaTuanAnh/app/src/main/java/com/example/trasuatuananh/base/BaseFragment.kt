package com.example.trasuatuananh.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import com.example.trasuatuananh.R
import com.example.trasuatuananh.util.LifeCycleExt.launchWhenResumed
import com.example.trasuatuananh.util.getBackStackEntryIndex
import com.example.trasuatuananh.widget.dialog.AlertDialogListener


abstract class BaseFragment : Fragment(), BaseScreen, ScreenNavigator {
    private var onFragmentCloseCallback: OnFragmentCloseCallback? = null
    private var resultCode = Activity.RESULT_CANCELED
    private var resultData: Intent? = null

    private val launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { intent ->
                onActivityDataResult(intent)
            }
        }

    open fun onActivityDataResult(data: Intent) = Unit

    fun launchActivityForResult(intent: Intent) {
        launchSomeActivity.launch(intent)
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

    private fun hideLoadingDialog() {
        (activity as? BaseActivity)?.hideLoadingDialog()
    }

    @JvmOverloads
    fun showLoadingDialog(cancelable: Boolean = true) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showLoadingDialog()
        }
    }

    fun goToDialogFragment(mBaseDialog: BaseDialogFragment, mBundle: Bundle?) {
        val fragmentManager = getChildFragmentManager()
        mBaseDialog.setArguments(mBundle)
        mBaseDialog.show(fragmentManager, mBaseDialog.javaClass.getName())
    }

    fun goToFragment(
        @IdRes fragmentContainerId: Int,
        mBaseFragment: BaseFragment,
        mBundle: Bundle?
    ) {
        addFragment(
            requireActivity().supportFragmentManager,
            mBaseFragment,
            fragmentContainerId,
            mBundle
        )
    }

    open fun onBackFragment() {
        popBackStack()
    }

    fun openActivityForResult(clazz: Class<*>?, bundle: Bundle?, requestCode: Int) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.openActivityForResult(clazz, bundle, requestCode)
        }
    }

    fun openActivity(clazz: Class<*>?, isFinish: Boolean) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.openActivity(clazz, isFinish)
        }
    }

    @JvmOverloads
    fun openActivity(clazz: Class<*>?, bundle: Bundle? = null) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.openActivity(clazz, bundle)
        }
    }

    fun showAlertDialog(message: String?) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showAlertDialog(
                message,
                getString(R.string.common_close),
                null
            )
        }
    }

    fun showAlertDialog(
        @StringRes title: Int, @StringRes message: Int,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        showAlertDialog(getString(title), getString(message), cancellable, listener)
    }

    fun showAlertDialog(
        @StringRes title: Int, @StringRes message: Int,
        @StringRes acceptLabel: Int, @StringRes cancelLabel: Int,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showAlertDialog(
                title,
                message,
                acceptLabel,
                cancelLabel,
                cancellable,
                listener
            )
        }
    }

    fun showAlertDialog(
        message: String?,
        @StringRes acceptLabel: Int, @StringRes cancelLabel: Int,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showAlertDialog(
                message,
                acceptLabel, cancelLabel, cancellable, listener
            )
        }
    }

    fun showAlertWithCustom(
        title: String?, message: String?,
        acceptLabel: String?, cancelLabel: String?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity?)?.showAlertDialog(
                title, message,
                acceptLabel, cancelLabel, cancellable, listener
            )
        }
    }


    fun showAlertDialog(
        title: String?, message: String?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showAlertDialog(title, message, cancellable, listener)
        }
    }

    open fun showAlertDialog(
        title: String?, message: String?,
        acceptLabel: String?, cancelLabel: String?,
        cancellable: Boolean,
        listener: AlertDialogListener?
    ) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showAlertDialog(
                title, message, acceptLabel,
                cancelLabel, cancellable, listener
            )
        }
    }

    fun showShortToast(@StringRes message: Int) {
        showShortToast(getString(message))
    }

    fun showLongToast(@StringRes message: Int) {
        showLongToast(getString(message))
    }

    fun showShortToast(message: String?) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showShortToast(message)
        }
    }

    fun showLongToast(message: String?) {
        if (activity != null && activity is BaseActivity) {
            (activity as? BaseActivity)?.showLongToast(message)
        }
    }


    fun setOnFragmentCloseCallback(onFragmentCloseCallback: OnFragmentCloseCallback?) {
        this.onFragmentCloseCallback = onFragmentCloseCallback
    }

    fun setResult(resultCode: Int, resultData: Intent?) {
        this.resultCode = resultCode
        this.resultData = resultData
    }

    fun setResult(resultCode: Int) {
        this.resultCode = resultCode
    }

    fun finish() {
        if (activity != null && activity is BaseActivity) {
            onFragmentCloseCallback?.onClose(resultCode, resultData)
            (activity as? BaseActivity)?.onBackFragment()
        }
    }

    fun addFragmentAndRemoveSelf(fragment: Fragment, @IdRes containerViewId: Int) {
        getParentFragmentManager().beginTransaction()
            .add(containerViewId, fragment, fragment.javaClass.getName())
            .remove(this).commit()
    }

    fun popBackStack(flags: Int) {
        popBackStack(null, flags)
    }

    @JvmOverloads
    fun popBackStack(name: String? = null, flags: Int = 0) {
        try {
            popBackStack(requireActivity().supportFragmentManager, name, flags)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun popBackStack(fragmentManager: FragmentManager, name: String?, flags: Int) {
        launchWhenResumed {
            fragmentManager.popBackStack(name, flags)
        }
    }

    fun popBackStack(fragmentManager: FragmentManager, name: String?) {
        popBackStack(fragmentManager, name, 0)
    }

    fun popBackStack(fragmentManager: FragmentManager) {
        popBackStack(fragmentManager, null, 0)
    }

    fun popBackStackToPreviousFragment(className: String) {
        try {
            popBackStackToPreviousFragment(requireActivity().supportFragmentManager, className)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBackStackEntryIndex(className: String): Int {
        return requireActivity().supportFragmentManager.getBackStackEntryIndex(className)
    }

    fun popBackStackToPreviousFragment(fragmentManager: FragmentManager, className: String) {
        val backStackEntryIndex = fragmentManager.getBackStackEntryIndex(className)
        if (backStackEntryIndex > 0) {
            popBackStack(
                fragmentManager,
                fragmentManager.getBackStackEntryAt(backStackEntryIndex - 1).name
            )
        }
    }

    fun addFragmentForResult(
        fragment: Fragment,
        @IdRes containerViewId: Int,
        requestKey: String,
        listener: FragmentResultListener
    ) {
        activity?.supportFragmentManager?.let {
            addFragmentForResult(
                it,
                fragment,
                containerViewId,
                requestKey,
                listener
            )
        }
    }

    fun addFragmentForResult(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        @IdRes containerViewId: Int,
        requestKey: String,
        listener: FragmentResultListener
    ) {
        fragmentManager.setFragmentResultListener(requestKey, getViewLifecycleOwner(), listener)
        if (activity != null) fragmentManager.beginTransaction()
            .add(containerViewId, fragment, fragment.javaClass.getName())
            .addToBackStack(fragment.javaClass.getName())
            .commitAllowingStateLoss()
    }

    fun openDialogFragmentForResult(
        fragmentManager: FragmentManager,
        dialog: DialogFragment,
        requestKey: String,
        listener: FragmentResultListener
    ) {
        fragmentManager.setFragmentResultListener(requestKey, getViewLifecycleOwner(), listener)
        dialog.show(fragmentManager, dialog.javaClass.getName())
    }

    @JvmOverloads
    fun addFragment(fragment: Fragment, @IdRes containerViewId: Int, bundle: Bundle? = null) {
        activity?.supportFragmentManager?.let { addFragment(it, fragment, containerViewId, bundle) }
    }

    @JvmOverloads
    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        @IdRes containerViewId: Int,
        bundle: Bundle? = null
    ) {
        if (bundle != null) {
            if (fragment.arguments == null)
                fragment.setArguments(bundle)
            else
                fragment.arguments?.putAll(bundle)
        }
        if (activity != null)
            fragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragment.javaClass.getName())
                .addToBackStack(fragment.javaClass.getName())
                .commitAllowingStateLoss()
    }

    fun addFragmentAndHideItSelf(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        @IdRes containerViewId: Int
    ) {
        if (activity != null)
            fragmentManager.beginTransaction()
                .add(containerViewId, fragment, fragment.javaClass.getName())
                .hide(this)
                .addToBackStack(fragment.javaClass.getName())
                .commitAllowingStateLoss()
    }

    @JvmOverloads
    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        @IdRes containerViewId: Int,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    ) {
        if (activity != null) {
            if (bundle != null) {
                if (fragment.arguments == null)
                    fragment.setArguments(bundle)
                else
                    fragment.arguments?.putAll(bundle)
            }

            fragmentManager.beginTransaction()
                .replace(containerViewId, fragment, fragment.javaClass.getName()).apply {
                    if (addToBackStack)
                        addToBackStack(fragment.javaClass.getName())
                }.commitAllowingStateLoss()
        }
    }

    @JvmOverloads
    fun replaceFragment(fragment: Fragment, @IdRes containerViewId: Int, bundle: Bundle? = null) {
        replaceFragment(requireActivity().supportFragmentManager, fragment, containerViewId, bundle)
    }

    open fun shouldProtectedCaptureScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getChildFragmentManager().addFragmentOnAttachListener { fragmentManager: FragmentManager?, fragment: Fragment? ->
            if (fragment is BaseFragment && activity != null) {
                if (fragment.shouldProtectedCaptureScreen()) {
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                } else {
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }
        }
    }

    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) removeFragment(getChildFragmentManager(), fragment)
    }

    fun removeFragment(tag: String?) {
        removeFragment(getChildFragmentManager(), tag)
    }

    fun removeFragment(fragmentManager: FragmentManager, tag: String?) {
        val fragment = fragmentManager.findFragmentByTag(tag)
        fragment?.let { removeFragment(fragmentManager, it) }
    }

    fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }

    fun replaceFragment(fragment: Fragment, @IdRes containerViewId: Int, addToBackStack: Boolean) {
        if (activity == null) return
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(fragment.javaClass.getName())
        transaction.replace(containerViewId, fragment, fragment.javaClass.getName())
            .commitAllowingStateLoss()
    }

    override fun backToRootStack() {
        popBackStack(FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
