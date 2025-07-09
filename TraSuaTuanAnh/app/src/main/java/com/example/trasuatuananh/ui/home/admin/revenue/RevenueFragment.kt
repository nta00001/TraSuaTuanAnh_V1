package com.example.trasuatuananh.ui.home.admin.revenue

import android.graphics.Color
import android.widget.TextView
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.response.RevenueResponse
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.databinding.FragmentRevenueBinding
import com.example.trasuatuananh.ui.home.admin.revenue.adapter.RevenueAdapter
import com.example.trasuatuananh.util.DayValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class RevenueFragment : BaseDataBindFragment<FragmentRevenueBinding, RevenueContract.Presenter>(),
    RevenueContract.View {
    companion object {
        fun newInstance() = RevenueFragment()
    }

    private val adapter: RevenueAdapter by lazy {
        RevenueAdapter()
    }

    override fun getLayoutId() = R.layout.fragment_revenue

    override fun initView() {
        mBinding?.apply {
            rvRevenue.adapter = adapter

            // Danh sách các TextView cần xử lý
            val textViews = listOf(txtWeek, txtMonth, txtYear)

            // Hàm cập nhật background
            fun updateBackground(selectedTextView: TextView) {
                for (textView in textViews) {
                    if (textView == selectedTextView) {
                        textView.setBackgroundResource(R.drawable.bg_border_corner_lucky_money_view)
                    } else {
                        textView.setBackgroundResource(R.drawable.bg_button_disable)
                    }
                }
            }

            txtWeek.setOnClickListener {
                mPresenter?.getRevenueData(7)
                mPresenter?.setTimePeriod(getString(R.string.last_7_days))
                updateBackground(txtWeek)
            }

            txtMonth.setOnClickListener {
                mPresenter?.getRevenueData(30)
                mPresenter?.setTimePeriod(getString(R.string.last_30_days))
                updateBackground(txtMonth)
            }

            txtYear.setOnClickListener {
                mPresenter?.getRevenueData(365)
                mPresenter?.setTimePeriod(getString(R.string.last_1_year))
                updateBackground(txtYear)
            }
        }
    }


    override fun initData() {
        mPresenter = RevenuePresenter(
            this,
            FoodRepositoryImpl()
        ).apply {
            mBinding?.presenter = this
            getRevenueData(7)
            listRevenue().observe(viewLifecycleOwner) {
                adapter.setData(it)
                setBarChart(it)

            }
        }
    }

    override fun getStringRes(id: Int): String {
        return getString(id)
    }

    private fun setBarChart(list: List<RevenueResponse>) {
        // Chuyển đổi dữ liệu RevenueResponse thành BarEntry
        val barEntries = list.mapIndexed { index, revenue ->
            BarEntry(index.toFloat(), revenue.tien.toFloat()) // Sử dụng index làm trục x
        }

        // Tạo BarDataSet
        val barDataSet = BarDataSet(barEntries, "Doanh Thu Theo Ngày")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 12f

        // Tạo BarData và gắn vào BarChart
        val barData = BarData(barDataSet)
        mBinding?.barChart?.apply {
            data = barData

            // Cấu hình BarChart
            description.isEnabled = false
            setFitBars(true) // Đảm bảo các cột vừa khít với biểu đồ
            animateY(1000)
            xAxis.apply {
                granularity = 1f // Đảm bảo mỗi cột cách đều nhau
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter =
                    DayValueFormatter(list) // Sử dụng DayValueFormatter để hiển thị chỉ ngày
            }
            axisLeft.axisMinimum = 0f // Bắt đầu trục y từ 0
            axisRight.isEnabled = false // Ẩn trục y bên phải

            // Hiển thị BarChart
            invalidate()
        }
    }


}