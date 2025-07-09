package com.example.trasuatuananh.ui.home.bestselling

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils

class BestSellingPresenter(
    private val view: BestSellingContract.View,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view), BestSellingContract.Presenter {
    private var _listHotFood = MutableLiveData<List<Food>>()
    private var _userName = MutableLiveData(
        "Xin chào: ${SharedPreferencesUtils.get(Constants.KEY.KEY_USER_NAME, "")}"
    )

    override fun listBestSelling() = _listHotFood
    override fun getUserName() = _userName


    override fun getListBestSelling() {
        baseCallApi(foodRepository.getListHotFood(),
            onSuccess = { response ->
                _listHotFood.value = response?.data
            },
            onError = {

            })
    }
}