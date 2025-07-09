package com.example.trasuatuananh.ui.home.homegroup

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.account.AccountRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.base.LocalBroadcastReceiver
import com.example.trasuatuananh.base.OrderEnabledLocalBroadcastManager
import com.example.trasuatuananh.databinding.FragmentHomeBinding
import com.example.trasuatuananh.ui.home.homegroup.adapter.HomeGroupAdapter
import com.example.trasuatuananh.ui.home.homegroup.adapter.MarginItemDecoration
import com.example.trasuatuananh.ui.home.homegroup.bottomsheet.CartBottomSheet
import com.example.trasuatuananh.ui.payment.PaymentFragment
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.example.trasuatuananh.util.StringUtils
import com.example.trasuatuananh.widget.dialog.AlertDialogListener
import com.google.gson.reflect.TypeToken

class HomeGroupFragment :
    BaseDataBindFragment<FragmentHomeBinding, HomeGroupContract.Presenter>(),
    HomeGroupContract.View {
    companion object {
        fun newInstance() = HomeGroupFragment()
    }

    private val adapter by lazy {
        HomeGroupAdapter(getBaseActivity())
    }
    private val cartReceiver: LocalBroadcastReceiver by lazy {
        object : LocalBroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Constants.Actions.NOTIFY_SHOW_CART -> {
                        val listFoodJson =
                            intent.getStringExtra(Constants.BundleConstants.LIST_FOOD)
                        listFoodJson?.let {
                            val type = object : TypeToken<MutableList<Food>>() {}.type
                            val listFood: MutableList<Food> =
                                StringUtils.stringToObject(listFoodJson, type)
                            showLayoutCart(listFood)
                        }
                    }

                    Constants.Actions.NOTIFY_HIDE_CART -> hideLayoutCart()
                }
            }
        }
    }


    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        mBinding?.apply {
            rvHomeGroup.adapter = adapter
            rvHomeGroup.addItemDecoration(
                MarginItemDecoration(
                    requireContext(),
                    R.dimen.d_8,
                    R.dimen.d_4
                )
            )
            llCart.setOnClickListener {
                val listItem = mPresenter?.listFood()?.value
                listItem?.let { it1 ->
                    showBottomSheet(it1)
                }
            }
            val intentFilter = IntentFilter().apply {
                addAction(Constants.Actions.NOTIFY_SHOW_CART)
                addAction(Constants.Actions.NOTIFY_HIDE_CART)
            }
            btnPay.setOnClickListener {
                mPresenter?.listFood()?.value?.let { it1 ->
                    PaymentFragment.newInstance(
                        it1
                    )
                }?.let { it2 ->
                    getBaseActivity().addFragment(it2, R.id.flMain)
                }
            }



            OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity()).registerReceiver(
                cartReceiver,
                intentFilter
            )
        }
        // back click
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
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
            })

    }

    override fun initData() {
        mPresenter = HomeGroupPresenter(this,
            AccountRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
            getUserInfo()
        }
        mPresenter?.listFragment()?.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun showLayoutCart(listFood: List<Food>) {
        val nonEmptyList = listFood.filter { it.quantity > 0 }
        mPresenter?.setListFood(nonEmptyList)
        mPresenter?.setIsCartEmpty(true)
        mBinding?.nestedScrollViewHomeGroup?.apply {
            (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = 300
        }
    }


    private fun hideLayoutCart() {
        mPresenter?.setIsCartEmpty(false)
        mBinding?.nestedScrollViewHomeGroup?.apply {
            (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = 0
        }
    }

    private fun showBottomSheet(listFood: List<Food>) {
        val nonEmptyList = listFood.filter { it.quantity > 0 }
        val cartBottomSheet = CartBottomSheet.newInstance(nonEmptyList)
        cartBottomSheet.show(parentFragmentManager, CartBottomSheet.TAG)
    }

    override fun onDestroy() {
        try {
            OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity())
                .unregisterReceiver(cartReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}