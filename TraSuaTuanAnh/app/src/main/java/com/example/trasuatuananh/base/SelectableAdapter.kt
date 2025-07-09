package com.example.trasuatuananh.base

import androidx.databinding.ViewDataBinding
import com.example.trasuatuananh.api.model.Selectable

abstract class SelectableAdapter<T, VDB : ViewDataBinding>(
    contentsTheSame: ((Selectable<T>, Selectable<T>) -> Boolean),
    itemsTheSame: ((Selectable<T>, Selectable<T>) -> Boolean)? = null,
    onClickListener: ((Selectable<T>, Int) -> Unit)? = null
) : AbsListAdapter<Selectable<T>, VDB>(contentsTheSame, itemsTheSame, onClickListener)