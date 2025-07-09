package com.example.trasuatuananh.ui.home.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationPresenter(
    private val view: LocationContract.View
) : CommonPresenter(view, view), LocationContract.Presenter {
    private val fusedLocationProviderClient =
        view.getViewContext()?.let {
            LocationServices.getFusedLocationProviderClient(it)
        }
    private val _address = MutableLiveData<String>()


    override fun address() = _address
    override fun setAddress(address: String) {
        _address.value = address
    }

    override fun getCurrentLocationAndAddress() {

        if (view.getViewContext()
                ?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient?.lastLocation?.addOnCompleteListener { task ->
                val location: Location? = task.result
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Use Geocoder to get address
                    val geocoder = Geocoder(view.getViewContext()!!, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0].getAddressLine(0)
                        SharedPreferencesUtils.put(Constants.KEY.KEY_ADDRESS, address)
                        _address.value = view.getStringRes(R.string.text_address, address)
                    } else {
                        view.showError("Address not found")
                    }
                }
            }
        } else {
            view.requestLocationPermission()
        }
    }

    // Can be called when the permission is granted
    override fun onPermissionGranted() {
        getCurrentLocationAndAddress()
    }
}