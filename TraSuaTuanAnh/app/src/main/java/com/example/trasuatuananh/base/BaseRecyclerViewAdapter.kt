package com.example.trasuatuananh.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.trasuatuananh.base.BindingViewHolder

abstract class BaseRecyclerViewAdapter<E, VDB : ViewDataBinding>(private val onClickListener: ((E, Int) -> Unit)? = null) :
    RecyclerView.Adapter<BindingViewHolder<VDB>>() {

    @get:LayoutRes
    abstract val itemLayoutRes: Int
    abstract fun onBindData(item: E, position: Int, viewDataBinding: VDB)

    private val datas = mutableListOf<E>()

    fun setData(items: List<E>) {
        datas.clear()
        datas.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(item: E) {
        datas.add(item)
        notifyDataSetChanged()
    }

    fun addListData(item: List<E>) {
        datas.addAll(item)
    }

    fun getItem(position: Int) = datas[position]
    override fun getItemCount() = datas.size

    override fun onBindViewHolder(viewHolder: BindingViewHolder<VDB>, position: Int) {
        val item = getItem(position)
        onBindData(item, position, viewHolder.viewDataBinding)

        viewHolder.itemView.setOnClickListener {
            onClickListener?.invoke(item, position)
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BindingViewHolder<VDB> {
        val viewDataBinding = DataBindingUtil.inflate<VDB>(
            LayoutInflater.from(viewGroup.context),
            itemLayoutRes,
            viewGroup,
            false
        )

        return BindingViewHolder(viewDataBinding)
    }

    fun clearData() {
        datas.clear()
        notifyDataSetChanged()
    }
}