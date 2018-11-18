package com.example.initialproject.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.initialproject.model.RequestListener;
import com.example.initialproject.model.RequestModelImplement;
import com.example.initialproject.view.MyView;

public class PresenterImplement implements Presenter, RequestListener {
    private RequestModelImplement requestModelImplement;
    private MyView myView;

    public PresenterImplement(MyView myView) {
        this.myView = myView;
        this.requestModelImplement = new RequestModelImplement();
    }

    @Override
    public void loadData(String url) {
        requestModelImplement.getData(PresenterImplement.this, url);
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
