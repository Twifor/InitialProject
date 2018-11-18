package com.example.initialproject.model;

public interface RequestListener {
    void onSuccess(String data);

    void onFail();
}
