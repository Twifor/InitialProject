package com.example.initialproject.model


interface RequestModel {
    fun getData(requestListener: RequestListener, url: String, type: Int)
}