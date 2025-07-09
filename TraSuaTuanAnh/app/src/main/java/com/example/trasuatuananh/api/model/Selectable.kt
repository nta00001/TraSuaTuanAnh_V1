package com.example.trasuatuananh.api.model

import java.io.Serializable

open class Selectable<T>(val data: T) : Serializable {
    var selected = false
}

interface Selectable2<T> {
    val data: T
    var selected: Boolean
}