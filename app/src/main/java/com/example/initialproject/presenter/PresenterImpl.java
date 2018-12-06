package com.example.initialproject.presenter;

import com.example.initialproject.model.RequestListener;
import com.example.initialproject.model.RequestModelImpl;
import com.example.initialproject.view.MyView;

public class PresenterImpl implements Presenter, RequestListener {
    private RequestModelImpl requestModelImpl;
    private MyView myView;

    public PresenterImpl(MyView myView) {
        this.myView = myView;
        this.requestModelImpl = new RequestModelImpl();
    }

    @Override
    public void loadData(String url) {
        requestModelImpl.getData(PresenterImpl.this, url);
    }

    @Override
    public void onSuccess(String data) {
        myView.showData(data);
    }

    @Override
    public void onFail() {
        myView.showData("error");
    }
}
