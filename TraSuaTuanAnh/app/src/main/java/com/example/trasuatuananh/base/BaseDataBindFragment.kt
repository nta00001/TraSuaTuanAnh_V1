package com.example.trasuatuananh.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.trasuatuananh.ui.main.MainActivity


abstract class BaseDataBindFragment<T : ViewDataBinding, K : BasePresenter> : BaseFragment(),
    AppBehaviorOnServiceError, BackClickHandler {

    protected var mBinding: T? = null
    protected var mPresenter: K? = null
    protected var mActivity: AppCompatActivity? = null
    private val uiHandleServiceError: AppBehaviorOnServiceError = CommonNewUIAppBehaviorOnServiceError(this)

     fun getUiHandleServiceError(): AppBehaviorOnServiceError = uiHandleServiceError

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding?.lifecycleOwner = viewLifecycleOwner
        initView()
        return mBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context as? AppCompatActivity
        }
    }

    override fun onStart() {
        super.onStart()
        if (mPresenter is BasePresenter) {
            (mPresenter as BasePresenter).subscribe()
        }
    }

    override fun onResume() {
        super.onResume()
        view?.apply {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, _, _ -> false }
        }
    }

    override fun onDestroy() {
        if (mPresenter is BasePresenter) {
            mPresenter?.unSubscribe()
        }
        super.onDestroy()
    }

    fun getBaseActivity(): BaseActivity{
        return (activity as? BaseActivity) ?: MainActivity.self()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    override fun handleServiceError(exception: BaseException) {
        if (isAdded) {
            getUiHandleServiceError().handleServiceError(exception)
        }
    }

    override fun handleOnBackPress(): Boolean {
        if (requireActivity().supportFragmentManager.backStackEntryCount == 0) {
            requireActivity().finish()
        } else {
            popBackStack()
        }
        return false
    }
}
