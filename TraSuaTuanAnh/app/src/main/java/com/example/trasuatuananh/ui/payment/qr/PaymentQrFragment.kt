package com.example.trasuatuananh.ui.payment.qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.api.repository.vietqr.VietQRRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.base.OrderEnabledLocalBroadcastManager
import com.example.trasuatuananh.databinding.FragmentPaymentQrBinding
import com.example.trasuatuananh.util.CommonToast
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.ScreenshotUtils
import com.example.trasuatuananh.util.StringUtils
import com.example.trasuatuananh.widget.dialog.AlertDialogListener

class PaymentQrFragment :
    BaseDataBindFragment<FragmentPaymentQrBinding, PaymentQrContract.Presenter>(),
    PaymentQrContract.View {
    companion object {
        private const val REQUEST_WRITE_STORAGE = 100
        private const val ARG_ADDRESS = "ARG_ADDRESS"
        private const val ARG_DISCOUNT_AMOUNT = "ARG_DISCOUNT_AMOUNT"
        private const val ARG_TOTAL_AMOUNT = "ARG_TOTAL_AMOUNT"
        private const val ARG_VOUCHER_ID = "ARG_VOUCHER_ID"
        private const val ARG_LIST_FOOD = "ARG_LIST_FOOD"


        fun newInstance(
            address: String?,
            discountAmount: String?,
            totalAmount: String?,
            voucherId: String?,
            listFood: List<Food>?
        ): PaymentQrFragment {
            val args = Bundle().apply {
                putString(ARG_ADDRESS, address)
                putString(ARG_DISCOUNT_AMOUNT, discountAmount)
                putString(ARG_TOTAL_AMOUNT, totalAmount)
                putString(ARG_VOUCHER_ID, voucherId)
                putParcelableArrayList(ARG_LIST_FOOD, ArrayList(listFood ?: emptyList()))
            }

            val fragment = PaymentQrFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.fragment_payment_qr

    override fun initView() {
        mBinding?.apply {
            toolbar.setOnBackClickListener {
                backClick()
            }
            btnContinue.setOnClickListener {
                presenter?.submitOrder()
            }
            btnSaveQR.setOnClickListener {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_WRITE_STORAGE
                        )
                        return@setOnClickListener // Chờ quyền được cấp, không thực hiện lưu
                    }
                }

                // Thực hiện lưu khi đã có quyền
                saveQrCode()
            }


        }

    }


    override fun initData() {
        val address = arguments?.getString(ARG_ADDRESS)
        val discountAmount = arguments?.getString(ARG_DISCOUNT_AMOUNT)
        val totalAmount = arguments?.getString(ARG_TOTAL_AMOUNT)
        val voucherId = arguments?.getString(ARG_VOUCHER_ID)
        val listFood = arguments?.getParcelableArrayList<Food>(ARG_LIST_FOOD)


        mPresenter = PaymentQrPresenter(
            this,
            address,
            discountAmount,
            totalAmount,
            voucherId,
            listFood,
            VietQRRepositoryImpl(),
            FoodRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
            getQrCode()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp
                CommonToast.showToast(
                    requireContext(),
                    "Đã cấp quyền lưu trữ!",
                    R.drawable.ic_checked_green
                )
            } else {
                // Quyền bị từ chối
                CommonToast.showToast(
                    requireContext(),
                    "Quyền lưu trữ bị từ chối!",
                    R.drawable.ic_check_box_red
                )
            }
        }
    }

    private fun saveQrCode() {
        hideViewToSaveAndShare()
        mBinding?.ctrlQR?.post {
            val filePath = ScreenshotUtils.saveViewAsImage(requireContext(), mBinding?.ctrlQR!!)
            visibleViewAfterSaveAndShare()

            if (filePath != null) {
                CommonToast.showToast(
                    requireContext(),
                    getString(R.string.save_image_success), R.drawable.ic_checked_green
                )
            } else {
                CommonToast.showToast(
                    requireContext(),
                    getString(R.string.save_image_failed), R.drawable.ic_check_box_red
                )
            }
        }
    }

    override fun showDiaLog(message: String) {
        getBaseActivity().showAlertDialogNew(
            icon = null,
            title = getString(R.string.app_notify_title),
            message = message,
            textTopButton = getString(R.string.common_close),
            isCancelable = false,
            listener =
            object : AlertDialogListener {
                override fun onAccept() {
                    getBaseActivity().onBackAllFragments()
                }

                override fun onCancel() {

                }
            }
        )
    }

    override fun setImageQr(bitmap: Bitmap) {
        mBinding?.qrImageView?.setImageBitmap(bitmap)
    }

    override fun showError(message: String) {

    }

    private fun backClick() {
        getBaseActivity().onBackFragment()
    }

    private fun hideViewToSaveAndShare() {
        mBinding?.apply {
            toolbar.visibility = View.INVISIBLE
            btnSaveQR.visibility = View.INVISIBLE
            btnContinue.visibility = View.INVISIBLE
        }
    }

    private fun visibleViewAfterSaveAndShare() {
        mBinding?.apply {
            toolbar.visibility = View.VISIBLE
            btnSaveQR.visibility = View.VISIBLE
            btnContinue.visibility = View.VISIBLE
        }
    }

    override fun sendListFoodBroadcast(list: List<Food>) {

        val broadcastIntent = Intent(Constants.Actions.NOTIFY_LIST_FOOD).apply {
            putExtra(
                Constants.BundleConstants.LIST_FOOD_CART,
                StringUtils.objectToString(list)
            )
        }
        context?.let { context ->
            OrderEnabledLocalBroadcastManager.getInstance(context)
                .sendBroadcast(broadcastIntent)
        }
        showDiaLog(getString(R.string.order_success))
    }


    override fun getStringRes(id: Int): String {
        return getString(id)
    }
}