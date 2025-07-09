package com.example.trasuatuananh.ui.basedatabind

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.AppBehaviorOnServiceErrorForActivity
import com.example.trasuatuananh.base.BaseActivity
import com.example.trasuatuananh.base.BaseException
import com.example.trasuatuananh.base.BasePresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseDataBindBottomSheet<VDB : ViewDataBinding, P : BasePresenter> :
    BottomSheetDialogFragment(), AppBehaviorOnServiceError {
    lateinit var presenter: P
    lateinit var binding: VDB

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun initView()
    abstract fun initData()

    val appBehaviorOnServiceError by lazy {
        try {
            (requireActivity() as? BaseActivity)?.let {
                AppBehaviorOnServiceErrorForActivity(it)
            }
        } catch (_: Exception) {
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        initData()
    }

    protected fun setCollapse(
        isCollapse: Boolean,
        animation: Boolean = true
    ) {
        val view: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) ?: return
        view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val behavior = BottomSheetBehavior.from(view)
        behavior.setPeekHeight(if (isCollapse) 0 else view.height, animation)
        behavior.state =
            if (isCollapse) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
    }

    protected fun openActivityForResult(
        resultLauncher: ActivityResultLauncher<Intent>, clazz: Class<*>?, bundle: Bundle? = null
    ) {
        val intent = Intent(requireContext(), clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        resultLauncher.launch(intent)
    }

    override fun handleServiceError(exception: BaseException) {
        appBehaviorOnServiceError?.handleServiceError(exception)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unSubscribe()
    }
}
