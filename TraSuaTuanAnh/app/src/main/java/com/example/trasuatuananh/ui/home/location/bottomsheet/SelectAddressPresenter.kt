package com.example.trasuatuananh.ui.home.location.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.address.QuanHuyen
import com.example.trasuatuananh.api.model.address.TinhTp
import com.example.trasuatuananh.api.model.address.XaPhuong
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.AddressUtils

class SelectAddressPresenter(
    private val view: SelectAddressContract.View
) : CommonPresenter(), SelectAddressContract.Presenter {

    private val _listProvinceCity = MutableLiveData<List<TinhTp>>()
    private val _listDistrict = MutableLiveData<List<QuanHuyen>>()
    private val _listWard = MutableLiveData<List<XaPhuong>>()
    private val _chooseProvinceCity =
        MutableLiveData<String>(view.getStringRes(R.string.choose_province_city, null))
    private val _chooseDistrict = MutableLiveData<String>()
    private val _chooseWard = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _isChooseDistrict: LiveData<Boolean> = Transformations.map(_chooseDistrict) {
        !it.isNullOrEmpty()
    }

    private val _isChooseWard: LiveData<Boolean> = Transformations.map(_chooseWard) {
        !it.isNullOrEmpty()
    }

    override fun getListProvinceCity() {
        _listProvinceCity.value = view.getViewContext()?.let {
            AddressUtils.getTinhList(it)
        }
    }

    override fun getListDistrict(provinceName: String) {
        setChooseProvinceCity(provinceName)
        setChooseDistrict(view.getStringRes(R.string.choose_district, null))

        val provinceCode = _listProvinceCity.value
            ?.firstOrNull { it.name == provinceName }
            ?.code

        if (provinceCode != null) {
            _listDistrict.value = view.getViewContext()?.let {
                AddressUtils.getQuanHuyenListByTinh(it, provinceCode)
            } ?: emptyList()  // Trả về danh sách trống nếu context là null
        } else {
            _listDistrict.value = emptyList()  // Trường hợp không tìm thấy tỉnh phù hợp
        }
    }


    override fun getListWard(districtName: String) {
        setChooseDistrict(districtName)
        setChooseWard(view.getStringRes(R.string.choose_ward, null))

        val districtCode = _listDistrict.value
            ?.firstOrNull { it.name == districtName }
            ?.code

        if (districtCode != null) {
            _listWard.value = view.getViewContext()?.let {
                AddressUtils.getXaPhuongListByQuanHuyen(it, districtCode)
            } ?: emptyList()  // Trả về danh sách trống nếu context là null
        } else {
            _listWard.value = emptyList()  // Trường hợp không tìm thấy quận phù hợp
        }
    }



    override fun listProvinceCity() = _listProvinceCity

    override fun listDistrict() = _listDistrict

    override fun listWard() = _listWard

    override fun address() = _address
    override fun chooseProvinceCity() = _chooseProvinceCity
    override fun chooseDistrict() = _chooseDistrict
    override fun isChooseDistrict() = _isChooseDistrict
    override fun isChooseWard() = _isChooseWard
    override fun chooseWard() = _chooseWard

    override fun setChooseProvinceCity(value: String) {
        _chooseProvinceCity.value = value
    }

    override fun setChooseDistrict(value: String) {
        _chooseDistrict.value = value
    }

    override fun setChooseWard(value: String) {
        _chooseWard.value = value
    }

    override fun updateAddress() {
        _address.value = listOf(
            _chooseWard.value,
            _chooseDistrict.value,
            _chooseProvinceCity.value
        ).joinToString(", ")
    }
}