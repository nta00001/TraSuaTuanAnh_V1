package com.example.trasuatuananh.ui.home.admin.adapter

import android.view.View
import androidx.fragment.app.Fragment
import com.example.trasuatuananh.R
import com.example.trasuatuananh.base.BaseActivity
import com.example.trasuatuananh.databinding.ItemHomeAdminGroupBinding
import com.example.trasuatuananh.common.adapter.BaseRecyclerViewAdapter

class HomeAdminGroupAdapter(
    val baseActivity: BaseActivity
) : BaseRecyclerViewAdapter<Fragment, ItemHomeAdminGroupBinding>() {

    override val itemLayoutRes: Int = R.layout.item_home_admin_group

    override fun onBindData(
        item: Fragment,
        position: Int,
        viewDataBinding: ItemHomeAdminGroupBinding
    ) {
        val frameLayout = viewDataBinding.frItemFragment
        val uniqueId = View.generateViewId()
        frameLayout.id = uniqueId

        baseActivity.replaceFragment(item, uniqueId, false)
    }
}