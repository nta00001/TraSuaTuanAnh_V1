package com.example.trasuatuananh.ui.home.listfood

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.example.trasuatuananh.R
import com.example.trasuatuananh.api.model.Food
import com.example.trasuatuananh.api.repository.food.FoodRepositoryImpl
import com.example.trasuatuananh.base.BaseDataBindFragment
import com.example.trasuatuananh.base.LocalBroadcastReceiver
import com.example.trasuatuananh.base.OrderEnabledLocalBroadcastManager
import com.example.trasuatuananh.databinding.FragmentListFoodBinding
import com.example.trasuatuananh.ui.home.listfood.adapter.ListFoodAdapter
import com.example.trasuatuananh.ui.home.listfood.bottomsheet.IngredientTypeBottomSheet
import com.example.trasuatuananh.util.Constants
import com.example.trasuatuananh.util.StringUtils
import com.google.gson.reflect.TypeToken

class ListFoodFragment :
    BaseDataBindFragment<FragmentListFoodBinding, ListFoodContract.Presenter>(),
    ListFoodContract.View {
    companion object {
        fun newInstance() = ListFoodFragment()
    }

    private val listFoodReceiver: LocalBroadcastReceiver by lazy {
        object : LocalBroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val listFoodJson =
                    intent.getStringExtra(Constants.BundleConstants.LIST_FOOD_CART)
                listFoodJson?.let {
                    val type = object : TypeToken<MutableList<Food>>() {}.type
                    val listFood: MutableList<Food> =
                        StringUtils.stringToObject(listFoodJson, type)
                    mPresenter?.updateListFood(listFood)
                }
            }

        }
    }

    private val adapter: ListFoodAdapter by lazy {
        ListFoodAdapter(onItemClickListener = { item, pos ->
            showBottomSheet(item)
        },
            onAddClickListener = { item, pos ->
                mPresenter?.addAmountfood(item)
                adapter.notifyItemChanged(pos)
            },
            onSubClickListener = { item, pos ->
                mPresenter?.subAmountfood(item)
                adapter.notifyItemChanged(pos)
            }
        )
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_food

    override fun initView() {
        mBinding?.apply {
            rvListFood.adapter = adapter
        }

        OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity()).registerReceiver(
            listFoodReceiver,
            IntentFilter(Constants.Actions.NOTIFY_LIST_FOOD)
        )

    }

    override fun initData() {
        mPresenter = ListFoodPresenter(
            this,
            FoodRepositoryImpl()
        ).apply {
            getListFood()
        }

        mPresenter?.listFood()?.observe(this) { listFood ->
            listFood?.let {
                notifyFoodCardState(it)
                adapter.submitList(it)
            }
        }
    }

    private fun notifyFoodCardState(listFood: MutableList<Food>) {
        mPresenter?.getBroadcastAction(listFood)?.let { (action, filteredList) ->
            val broadcastIntent = Intent(action).apply {
                putExtra(
                    Constants.BundleConstants.LIST_FOOD,
                    StringUtils.objectToString(filteredList)
                )
            }
            context?.let {
                OrderEnabledLocalBroadcastManager.getInstance(it).sendBroadcast(broadcastIntent)
            }
        }
    }

    override fun showBottomSheet(food: Food) {
        val ingredientTypeBottomSheet =
            IngredientTypeBottomSheet.newInstance(food) { food ->
                food?.let {
                    mPresenter?.updateFood(it)
                }
            }


        ingredientTypeBottomSheet.show(parentFragmentManager, IngredientTypeBottomSheet.TAG)
    }

    override fun onDestroy() {
        try {
            OrderEnabledLocalBroadcastManager.getInstance(getBaseActivity())
                .unregisterReceiver(listFoodReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}