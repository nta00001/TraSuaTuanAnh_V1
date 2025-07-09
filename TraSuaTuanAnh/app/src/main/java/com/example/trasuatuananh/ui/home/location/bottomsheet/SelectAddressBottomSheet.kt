package com.example.trasuatuananh.ui.home.location.bottomsheet

import android.content.Context
import android.view.View
import com.example.trasuatuananh.R
import com.example.trasuatuananh.databinding.BottomSheetSelectAddressBinding
import com.example.trasuatuananh.ui.home.location.adapter.SelectAddressAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.example.trasuatuananh.ui.basedatabind.BaseDataBindBottomSheet

class SelectAddressBottomSheet(
    private val resultAddress: ((String?) -> Unit)? = null
) : BaseDataBindBottomSheet<BottomSheetSelectAddressBinding, SelectAddressContract.Presenter>(),
    SelectAddressContract.View {
    companion object {

        const val TAG = "SelectAddressBottomSheet"
        fun newInstance(
            resultAddress: ((String?) -> Unit)?
        ): SelectAddressBottomSheet {
            val fragment = SelectAddressBottomSheet(resultAddress)
            return fragment
        }

    }

    private val adapter: SelectAddressAdapter by lazy {
        SelectAddressAdapter(
            onClickListener = { item, pos ->

                if (presenter.listDistrict().value.isNullOrEmpty() && presenter.listWard().value.isNullOrEmpty()) {
                    presenter.getListDistrict(item)
                } else if (presenter.listWard().value.isNullOrEmpty()) {
                    presenter.getListWard(item)
                } else {
                    presenter.setChooseWard(item)
                    binding.rvAddress.visibility = View.GONE
                }
            }
        )
    }
    override val layoutRes: Int
        get() = R.layout.bottom_sheet_select_address

    override fun initView() {
        binding.apply {
            imgClose.setOnClickListener {
                dismiss()
            }
            btnContinue.setOnClickListener {
                presenter?.updateAddress()
                resultAddress?.invoke(presenter?.address()?.value)
                dismiss()
            }
            rvAddress.adapter = adapter

        }
    }

    override fun initData() {
        presenter = SelectAddressPresenter(this).apply {
            binding.presenter = this
            getListProvinceCity()
            listProvinceCity().observe(viewLifecycleOwner) {
                val provinceCityNames = it.map { it.name }
                adapter.setData(provinceCityNames)
            }
            listDistrict().observe(viewLifecycleOwner) {
                val districtNames = it.map { it.name }
                adapter.setData(districtNames)
            }
            listWard().observe(viewLifecycleOwner) {
                val wardNames = it.map { it.name }
                adapter.setData(wardNames)
            }

        }
    }

    override fun getViewContext(): Context? {
        return context
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)

        bottomSheet.layoutParams.height = (resources.displayMetrics.heightPixels * 0.95).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    override fun getStringRes(id: Int, message: String?): String {
        return getString(id, message)
    }

    override fun showLoading() {}
    override fun hideLoading() {}
    override fun showMessage(message: String) {}
}