package com.example.initialproject.presenter

import com.example.initialproject.model.RequestListener
import com.example.initialproject.model.RequestModel
import com.example.initialproject.model.RequestModelImpl
import com.example.initialproject.view.MyView


class PresenterImpl(private val myView: MyView) : Presenter, RequestListener {
    private val requestModel: RequestModel = RequestModelImpl()

    override  fun loadData(url: String, type: Int) {
        requestModel.getData(this@PresenterImpl, url, type)
    }

    override fun onSuccess(data: String) {
        myView.showData(data)
    }

    override fun onFail() {
        myView.showData("error")
    }
}