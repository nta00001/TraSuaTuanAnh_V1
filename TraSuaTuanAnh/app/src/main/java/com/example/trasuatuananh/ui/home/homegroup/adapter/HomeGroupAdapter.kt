package com.example.trasuatuananh.ui.home.homegroup.adapter

import android.view.View
import androidx.fragment.app.Fragment
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseActivity
import com.example.trasuatuananh.databinding.ItemFragmentHomeBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter


class HomeGroupAdapter(
    val baseActivity: BaseActivity
) : BaseRecyclerViewAdapter<() -> Fragment, ItemFragmentHomeBinding>() {

    override val itemLayoutRes: Int = R.layout.item_fragment_home

    override fun onBindData(
        item: () -> Fragment,
        position: Int,
        viewDataBinding: ItemFragmentHomeBinding
    ) {
        val frameLayout = viewDataBinding.frItemFragment
        val uniqueId = View.generateViewId()
        frameLayout.id = uniqueId

        // Tạo mới fragment tại đây
        val fragment = item.invoke()

        baseActivity.replaceFragment(fragment, uniqueId, false)
    }
}
