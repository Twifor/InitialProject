package com.example.initialproject.model

interface RequestListener {
    fun onSuccess(data: String)

    fun onFail()
}
