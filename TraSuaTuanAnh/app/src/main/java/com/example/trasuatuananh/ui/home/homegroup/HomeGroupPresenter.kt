package com.example.trasuatuananh.ui.home.homegroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.account.AccountRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.ui.home.bestselling.BestSellingFragment
import com.example.trasuatuananh.ui.home.listfood.ListFoodFragment
import com.example.trasuatuananh.ui.home.location.LocationFragment
import com.example.trasuatuananh.ui.home.promotion.DiscountFragment
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.SharedPreferencesUtils
import com.example.trasuatuananh.util.StringUtils

class HomeGroupPresenter(
    private val view: HomeGroupContract.View,
    private val accountRepository: AccountRepository
) : CommonPresenter(view, view), HomeGroupContract.Presenter {
    private val _listFragment = MutableLiveData<List<() -> Fragment>>()
    private val _isCartEmpty = MutableLiveData<Boolean>(false)
    private val _listFood = MutableLiveData<List<Food>>()
    private val _totalAmount = MutableLiveData<String>("")
    private val _quantity = MutableLiveData<String>("")
    override fun totalAmount() = _totalAmount

    override fun quantity() = _quantity
    override fun getUserInfo() {
        baseCallApi(accountRepository.getAccount(),
            onSuccess = { response ->
                response?.data?.apply {
                    userId.let { SharedPreferencesUtils.put(Constants.KEY.KEY_USER_ID, it) }
                    phoneNumber.let {
                        SharedPreferencesUtils.put(
                            Constants.KEY.KEY_PHONE_NUMBER,
                            it
                        )
                    }
                    username.let { SharedPreferencesUtils.put(Constants.KEY.KEY_USER_NAME, it) }
                }
                getListFragmentHome()
            },
            onError = {

            }
        )
    }


    override fun listFood() = _listFood

    override fun setListFood(listFood: List<Food>) {
        _listFood.value = listFood
        calculateTotalAmount()
        updateQuantity()
    }

    override fun listFragment() = _listFragment
    override fun isCartEmpty() = _isCartEmpty

    override fun setIsCartEmpty(boolean: Boolean) {
        _isCartEmpty.value = boolean
    }

    override fun getListFragmentHome() {
        _listFragment.value = listOf(
            { LocationFragment.newInstance() },
//        { SearchFragment.newInstance() },
            { BestSellingFragment.newInstance() },
            { DiscountFragment.newInstance() },
            { ListFoodFragment.newInstance() }
        )
    }


    fun calculateTotalAmount() {
        _totalAmount.value = _listFood.value?.let {
            StringUtils.calculateTotalPrice(it)
        } ?: "0"
    }

    fun updateQuantity() {
        _quantity.value = _listFood.value?.size?.toString() ?: "0"
    }

}