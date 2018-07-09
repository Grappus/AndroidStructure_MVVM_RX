package com.grappus.android.basemvvm.listeners;


public interface APIResponseListener<T> {

    void onSuccess(String reqCode, T responseObject);

    void onError(String reqCode, int errorCode, String errorMessage);
}