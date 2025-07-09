package com.example.trasuatuananh.ui.home.listfood.bottomsheet

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.Config
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.databinding.BottomSheetIngredientTypeBinding
import com.example.trasuatuananh.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.trasuatuananh.ui.basedatabind.BaseDataBindBottomSheet

class IngredientTypeBottomSheet(
    private val resultIngredientType: ((Food?) -> Unit)? = null
) : BaseDataBindBottomSheet<BottomSheetIngredientTypeBinding, IngredientTypeContract.Presenter>(),
    IngredientTypeContract.View {

    companion object {
        const val TAG = "IngredientTypeBottomSheet"
        private const val ARG_FOOD_MODEL = "ARG_FOOD_MODEL"
        fun newInstance(
            food: Food,
            resultIngredientType: ((Food?) -> Unit)?
        ): IngredientTypeBottomSheet {
            val args = Bundle().apply {
                putParcelable(ARG_FOOD_MODEL, food)
            }
            val fragment = IngredientTypeBottomSheet(resultIngredientType)
            fragment.arguments = args
            return fragment
        }
    }

    private val food by lazy {
        arguments?.getParcelable<Food>(ARG_FOOD_MODEL)
    }

    override val layoutRes: Int = R.layout.bottom_sheet_ingredient_type


    override fun initView() {
        val behaviorBottoSheet =
            (this@IngredientTypeBottomSheet.dialog as BottomSheetDialog).behavior
        behaviorBottoSheet.state =
            BottomSheetBehavior.STATE_EXPANDED

        behaviorBottoSheet.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    behaviorBottoSheet.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.apply {
            Glide.with(ivFood.context)
                .load(Config.urlImage + food?.imageUrl)
                .placeholder(R.drawable.ic_null)
                .error(R.drawable.ic_null)
                .into(ivFood)

            tvTitle.text = food?.foodName
            txtDescription.text = food?.description
            cbGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.cbSizeM -> {
                        presenter?.setSize(Constants.Type.SIZE_M)
                    }

                    R.id.cbSizeL -> {
                        presenter?.setSize(Constants.Type.SIZE_L)
                    }
                }
            }
            when (food?.size) {
                Constants.Type.SIZE_M -> binding.cbSizeM.isChecked = true
                Constants.Type.SIZE_L -> binding.cbSizeL.isChecked = true
                else -> binding.cbSizeM.isChecked = true
            }
            btnSub.setOnClickListener {
                presenter?.subQuantity()
            }
            btnAdd.setOnClickListener {
                presenter?.addQuantity()
            }

            imgClose.setOnClickListener {
                dismiss()
            }

            btnContinue.setOnClickListener {
                presenter?.updateFood()
                resultIngredientType?.invoke(
                    presenter?.food()?.value
                )
                dismiss()
            }
        }

    }

    override fun initData() {
        presenter = IngredientTypePresenter(this, food).apply {
            binding.presenter = this
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showMessage(message: String) {}

}