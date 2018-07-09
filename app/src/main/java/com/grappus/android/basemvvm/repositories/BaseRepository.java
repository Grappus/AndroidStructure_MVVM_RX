package com.grappus.android.basemvvm.repositories;

import android.content.Context;
import android.util.Log;

import com.grappus.android.basemvvm.R;
import com.grappus.android.basemvvm.listeners.APIResponseListener;
import com.grappus.android.basemvvm.utils.Utils;

import io.reactivex.disposables.CompositeDisposable;


public class BaseRepository implements APIResponseListener {

    private final String TAG = BaseRepository.class.getSimpleName();

    public Context context;


    public CompositeDisposable networkDisposable;

    public void clearDisposable() {
        if (networkDisposable != null && !networkDisposable.isDisposed()) networkDisposable.clear();
    }


    public void showErrorMessage(String responseObject) {
        if (responseObject != null) {
            Utils.showToast(context, "" + responseObject);
        } else
            Utils.showToast(context, context.getString(R.string.error_general));
    }

    public void handelError(String endPoint, Throwable error) {
        if (error != null) {
            Log.e(TAG, "error: " + error.getLocalizedMessage());
            onError(endPoint, 0, error.getLocalizedMessage());
        }
    }


    @Override
    public void onSuccess(String reqCode, Object responseObject) {
        Utils.hideLoader();
    }

    @Override
    public void onError(String reqCode, int errorCode, String errorMessage) {
        Utils.hideLoader();
        showErrorMessage(errorMessage);
    }
}