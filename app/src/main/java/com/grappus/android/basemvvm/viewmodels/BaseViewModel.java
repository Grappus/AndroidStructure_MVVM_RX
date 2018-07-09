package com.grappus.android.basemvvm.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

public abstract class BaseViewModel extends AndroidViewModel {

    private final String TAG = BaseViewModel.class.getSimpleName();


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clearDisposable();
    }

    public Context getContext() {
        return getApplication().getApplicationContext();
    }


    public abstract void clearDisposable();
}