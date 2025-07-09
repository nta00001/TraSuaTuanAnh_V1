package com.example.trasuatuananh.ui.home.admin

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentHomeAdminBinding
import com.example.trasuatuananh.ui.home.admin.orderlist.OrderListFragment
import com.example.trasuatuananh.ui.home.admin.revenue.RevenueFragment
import com.example.trasuatuananh.ui.home.admin.setting.SettingFragment
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.example.trasuatuananh.widget.dialog.AlertDialogListener

class HomeAdminFragment :
    BaseDataBindFragment<FragmentHomeAdminBinding, HomeAdminContract.Presenter>(),
    HomeAdminContract.View {
    companion object {
        fun newInstance() = HomeAdminFragment()

    }

    override fun getLayoutId() = R.layout.fragment_home_admin

    override fun initView() {
        mBinding?.apply {
            toolbar.setOnClickListener {
                logOut()
            }

            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.list_food -> {
                        toolbar.setTitle(getString(R.string.order_list))
                        openFragment(OrderListFragment.newInstance())
                        true
                    }

                    R.id.statistics -> {
                        toolbar.setTitle(getString(R.string.statistics))
                        openFragment(RevenueFragment.newInstance())
                        true
                    }

                    R.id.setting -> {
                        toolbar.setTitle(getString(R.string.settings))
                        openFragment(SettingFragment.newInstance())
                        true
                    }

                    else -> false
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    logOut()
                }
            })
    }

    override fun initData() {
        mPresenter = HomeAdminPresenter(this).apply {
                openFragment(OrderListFragment.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        getBaseActivity().replaceFragment(fragment, R.id.flHomeAdmin, false)
    }

    private fun logOut() {
        getBaseActivity().showAlertDialogNew(
            icon = null,
            title = getString(R.string.app_notify_title),
            message = getString(R.string.log_out),
            textTopButton = getString(R.string.common_success),
            textBottomButton = getString(R.string.common_cancel),
            listener = object : AlertDialogListener {
                override fun onAccept() {
                    context?.let { TokenManager.saveToken(it, "") }
                    SharedPreferencesUtils.put(Constants.KEY.KEY_PHONE_NUMBER, "")
                    getBaseActivity().onBackFragment()
                }

                override fun onCancel() {

                }
            }
        )
    }
}