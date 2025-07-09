package com.example.trasuatuananh.ui.home.location.map

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.base.OrderEnabledLocalBroadcastManager
import com.example.trasuatuananh.databinding.FragmentAddressBinding
import com.example.trasuatuananh.ui.home.listfood.bottomsheet.IngredientTypeBottomSheet
import com.example.trasuatuananh.ui.home.location.bottomsheet.SelectAddressBottomSheet
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils

class AddressFragment : BaseDataBindFragment<FragmentAddressBinding, AddressContract.Presenter>(),
    AddressContract.View {
    companion object {
        fun newInstance() = AddressFragment()
    }

    override fun getLayoutId() = R.layout.fragment_address


    override fun initView() {
        mBinding?.apply {
            toolbar.setOnBackClickListener {
                onBackClick()
            }
            btnConfirm.setOnClickListener {
                mPresenter?.updateAddress()
                mPresenter?.address()?.value?.let { it1 ->
                    SharedPreferencesUtils.put(
                        Constants.KEY.KEY_ADDRESS,
                        it1
                    )
                }
                sendBocatLocation()
                onBackClick()

            }
            tvAddress.setOnClickListener {
                showBottomSheet()
            }
            ivAddress.setOnClickListener {
                showBottomSheet()
            }

            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        onBackClick()
                    }
                })

        }
    }

    override fun initData() {
        mPresenter = AddressPresenter(this).apply {
            mBinding?.presenter = this

        }

    }

    private fun showBottomSheet() {
        val selectAddressBottomSheet =
            SelectAddressBottomSheet.newInstance() { address ->
                mPresenter?.setChooseLocation(address.toString())
            }

        selectAddressBottomSheet.show(parentFragmentManager, IngredientTypeBottomSheet.TAG)

    }

    private fun sendBocatLocation() {
        val broadcastIntent = Intent(Constants.Actions.NOTIFY_UPDATE_LOCATION).apply {
            putExtra(Constants.BundleConstants.UPDATE_LOCATION, mPresenter?.address()?.value)
        }
        context?.let {
            OrderEnabledLocalBroadcastManager.getInstance(it).sendBroadcast(broadcastIntent)
        }
    }

    override fun getStringRes(id: Int): String {
        return getString(id)
    }

    override fun onBackClick() {
        getBaseActivity().onBackFragment()

    }


}