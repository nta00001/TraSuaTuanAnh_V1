package com.example.trasuatuananh.ui.home.admin.revenue

import androidx.lifecycle.MutableLiveData
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.api.repository.food.FoodRepository
import com.example.trasuatuananh.base.CommonPresenter
import com.example.trasuatuananh.util.DateUtils
import com.example.trasuatuananh.util.StringUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RevenuePresenter(
    private val view: RevenueContract.View,
    private val foodRepository: FoodRepository
) : CommonPresenter(view, view),
    RevenueContract.Presenter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val _listRevenue = MutableLiveData<List<RevenueResponse>>()
    private val _totalAmount = MutableLiveData<String>()
    private val _timePeriod = MutableLiveData<String>(view.getStringRes(R.string.last_7_days))

    override fun getRevenueData(daysBefore: Int) {
        val toDate = dateFormat.format(Calendar.getInstance().time)
        val fromDate = dateFormat.format(Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -daysBefore)
        }.time)

        baseCallApi(
            foodRepository.getRevenue(fromDate, toDate),
            onSuccess = {
                val formattedList = it?.map { revenue ->
                    revenue.copy(date = DateUtils.formatDate(revenue.date))
                }?.sortedByDescending { revenue ->
                    // Sắp xếp theo ngày mới nhất về trước
                    DateUtils.parseDate(revenue.date)
                }

                _listRevenue.postValue(formattedList)
                _totalAmount.value = it?.let { revenueList ->
                    StringUtils.calculateTotalFromStrings(revenueList)
                }
            },
            onError = {
                // Xử lý lỗi (nếu cần)
            }
        )
    }



    override fun listRevenue() = _listRevenue
    override fun totalAmount() = _totalAmount
    override fun timePeriod() = _timePeriod
    override fun setTimePeriod(value: String) {
        _timePeriod.value = value
    }
}