package com.example.trasuatuananh.ui.home.location.map

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.CommonPresenter

class AddressPresenter(
    private val view: AddressContract.View
) : CommonPresenter(), AddressContract.Presenter {
    private val _streetName = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _chooseLocation =
        MutableLiveData<String>(view.getStringRes(R.string.choose_location))

    override fun streetName() = _streetName
    override fun chooseLocation() = _chooseLocation
    override fun address() = _address

    override fun setStreetName(value: String) {
        _streetName.value = value
        updateAddress()
    }

    override fun setChooseLocation(value: String) {
        _chooseLocation.value = value
        updateAddress()
    }

    override fun updateAddress() {
        _address.value = "${_streetName.value}, ${_chooseLocation.value}"
    }

}