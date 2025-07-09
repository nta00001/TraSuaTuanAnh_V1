package com.example.trasuatuananh.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.example.trasuatuananh.base.BaseDataBindActivity
import com.example.trasuatuananh.databinding.ActivityMainBinding
import com.example.trasuatuananh.R
import com.example.trasuatuananh.ui.login.user.LoginFragment
import com.example.trasuatuananh.widget.dialog.AlertDialogListener


class MainActivity : BaseDataBindActivity<ActivityMainBinding, MainContract.Presenter>(),
    MainContract.View {

    companion object {
        private lateinit var instance: MainActivity

        fun self(): MainActivity {
            return instance
        }
    }

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        checkLocationPermission()
        replaceFragment(LoginFragment.newInstance(), R.id.flMain)
    }

    override fun initData() {

    }

    fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Yêu cầu quyền nếu chưa được cấp
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, có thể lấy vị trí
                // Thực hiện hành động khi quyền đã được cấp
            } else {
                // Quyền bị từ chối, kiểm tra xem người dùng đã chọn "Không bao giờ hỏi lại" chưa
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    // Người dùng vẫn có thể cấp quyền, yêu cầu lại quyền
                    showError(getString(R.string.location_permission_request))
                } else {
                    // Người dùng đã chọn "Không bao giờ hỏi lại"
                    showPermissionDeniedDialog()
                }
            }
        }
    }


    private fun showError(message: String) {
        this.showAlertDialogNew(
            icon = R.drawable.ic_unsuccessfull,
            title = getString(R.string.app_notify_title),
            message = message,
            textTopButton = getString(R.string.common_success),
            isCancelable = false,
            listener = object : AlertDialogListener {
                override fun onAccept() {
                    checkLocationPermission()
                }

                override fun onCancel() {

                }
            }
        )
    }

    private fun showPermissionDeniedDialog() {
        // Hiển thị hộp thoại thông báo yêu cầu người dùng cấp quyền trong cài đặt
        this.showAlertDialogNew(
            icon = R.drawable.ic_unsuccessfull,
            title = getString(R.string.app_notify_title),
            message = getString(R.string.location_permission_required),
            textTopButton = getString(R.string.common_open_settings),
            isCancelable = false,
            listener = object : AlertDialogListener {
                override fun onAccept() {
                    // Mở cài đặt ứng dụng cho người dùng
                    openAppSettings()
                }

                override fun onCancel() {
                    // Xử lý nếu người dùng từ chối
                }
            }
        )
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
        finish()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}



