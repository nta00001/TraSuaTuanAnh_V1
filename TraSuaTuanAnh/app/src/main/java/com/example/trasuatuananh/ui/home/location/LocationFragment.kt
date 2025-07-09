package com.example.trasuatuananh.ui.home.location

import TokenManager
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.base.LocalBroadcastReceiver
import com.example.trasuatuananh.base.OrderEnabledLocalBroadcastManager
import com.example.trasuatuananh.databinding.FragmentLocationBinding
import com.example.trasuatuananh.ui.info.InfoFragment
import com.example.trasuatuananh.ui.home.location.map.AddressFragment
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.example.trasuatuananh.widget.dialog.AlertDialogListener

class LocationFragment :
    BaseDataBindFragment<FragmentLocationBinding, LocationContract.Presenter>(),
    LocationContract.View {
    companion object {
        fun newInstance() = LocationFragment()
    }

    private val locationReceiver: LocalBroadcastReceiver by lazy {
        object : LocalBroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val address =
                    intent.getStringExtra(Constants.BundleConstants.UPDATE_LOCATION)
                address?.let {
                    mPresenter?.setAddress(it)
                }

            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_location

    override fun initView() {
        mBinding?.apply {
            txtTitle.setOnClickListener {
                getBaseActivity().addFragment(
                    AddressFragment.newInstance(), R.id.flMain
                )
            }

            ivInfo.setOnClickListener {
                getBaseActivity().replaceFragment(
                    InfoFragment.newInstance(), R.id.flMain
                )
            }

        }
        mBinding?.view = this

        OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity()).registerReceiver(
            locationReceiver,
            IntentFilter(Constants.Actions.NOTIFY_UPDATE_LOCATION)
        )

    }

    override fun initData() {
        mPresenter = LocationPresenter(this).apply {
            mBinding?.presenter = this
            val address = SharedPreferencesUtils.get(Constants.KEY.KEY_ADDRESS, "")
            if (address.isEmpty()) {
                getCurrentLocationAndAddress()
            } else {
                setAddress(getString(R.string.text_address, address))
            }
        }

    }

    override fun onBackClicked() {
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


    override fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    // Handle the result of permission request
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter?.onPermissionGranted()  // Retry fetching location and address
        } else {
            showError("Permission denied")
        }
    }

    override fun showError(message: String) {
        getBaseActivity().showAlertDialogNew(
            icon = R.drawable.ic_unsuccessfull,
            title = getString(R.string.app_notify_title),
            message = message,
            textTopButton = getString(R.string.common_close),
        )
    }


    override fun getStringRes(id: Int, message: String): String {
        return getString(id, message)
    }

    override fun getViewContext(): Context? {
        return context
    }

    override fun onDestroy() {
        try {
            OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity())
                .unregisterReceiver(locationReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}