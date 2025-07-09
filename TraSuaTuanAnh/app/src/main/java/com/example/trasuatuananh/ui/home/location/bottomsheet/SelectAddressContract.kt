package com.example.trasuatuananh.ui.home.location.bottomsheet

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.trasuatuananh.api.model.address.QuanHuyen
import com.example.trasuatuananh.api.model.address.TinhTp
import com.example.trasuatuananh.api.model.address.XaPhuong
import com.example.trasuatuananh.base.AppBehaviorOnServiceError
import com.example.trasuatuananh.base.BasePresenter
import com.example.trasuatuananh.base.BaseView

interface SelectAddressContract {
    interface View : BaseView, AppBehaviorOnServiceError {
        fun getStringRes(id: Int, message: String?): String
        fun getViewContext(): Context?

    }

    interface Presenter : BasePresenter {

        fun getListProvinceCity()
        fun getListDistrict(value: String)
        fun getListWard(value: String)

        fun listProvinceCity(): LiveData<List<TinhTp>>
        fun listDistrict(): LiveData<List<QuanHuyen>>
        fun listWard(): LiveData<List<XaPhuong>>
        fun address(): LiveData<String>
        fun chooseProvinceCity(): LiveData<String>
        fun chooseDistrict(): LiveData<String>
        fun isChooseDistrict(): LiveData<Boolean>
        fun isChooseWard(): LiveData<Boolean>
        fun chooseWard(): LiveData<String>
        fun setChooseProvinceCity(value: String)
        fun setChooseDistrict(value: String)
        fun setChooseWard(value: String)
        fun updateAddress()
    }
}