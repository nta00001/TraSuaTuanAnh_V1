package com.example.trasuatuananh.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class AbsListAdapter<T : Any, VDB : ViewDataBinding>(
    contentsTheSame: ((T, T) -> Boolean),
    itemsTheSame: ((T, T) -> Boolean)? = null,
    private val onClickListener: ((T, Int) -> Unit)? = null
) : ListAdapter<T, BindingViewHolder<VDB>>(object : DiffUtil.ItemCallback<T>() {

    override fun areContentsTheSame(item1: T, item2: T) =
        contentsTheSame.invoke(item1, item2)

    override fun areItemsTheSame(item1: T, item2: T) =
        itemsTheSame?.invoke(item1, item2) ?: (item1 == item2)
}) {
    @get:LayoutRes
    abstract val itemLayoutRes: Int
    abstract fun onBindData(item: T, position: Int, viewDataBinding: VDB)

    override fun onBindViewHolder(viewHolder: BindingViewHolder<VDB>, position: Int) {
        val item = getItem(position)

        viewHolder.itemView.setOnClickListener {
            onClickListener?.invoke(item, position)

            notifyItemChanged(position)
        }

        onBindData(item, position, viewHolder.viewDataBinding)
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
}