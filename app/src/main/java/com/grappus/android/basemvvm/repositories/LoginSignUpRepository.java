package com.grappus.android.basemvvm.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.grappus.android.basemvvm.controllers.network.APIController;
import com.grappus.android.basemvvm.controllers.network.models.request.SignUpRequest;
import com.grappus.android.basemvvm.controllers.network.models.response.AbstractResponse;
import com.grappus.android.basemvvm.utils.Constants;

import io.reactivex.disposables.CompositeDisposable;

public class LoginSignUpRepository extends BaseRepository implements Constants.APIEndPoints {


    private MutableLiveData<AbstractResponse> signUpData = new MutableLiveData<>();


    public LoginSignUpRepository(Context mContext) {
        context = mContext;
        networkDisposable = new CompositeDisposable();
    }


    public MutableLiveData<AbstractResponse> getSignUpData(SignUpRequest request) {
        networkDisposable.add(APIController.getInstance(context).signUp(request, this));
        return signUpData;
    }


    @Override
    public void onSuccess(String reqCode, Object responseObject) {
        super.onSuccess(reqCode, responseObject);

        if (reqCode.equals(API_LOGIN)) {
            if (responseObject != null) ; //Do Something

        } else if (reqCode.equals(API_SIGN_UP)) {
            if (responseObject != null)
                signUpData.setValue((AbstractResponse) responseObject);
        }
    }
}