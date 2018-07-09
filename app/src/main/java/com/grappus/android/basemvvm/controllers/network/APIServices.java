package com.grappus.android.basemvvm.controllers.network;


import com.grappus.android.basemvvm.controllers.network.models.request.SignUpRequest;
import com.grappus.android.basemvvm.controllers.network.models.response.AbstractResponse;
import com.grappus.android.basemvvm.utils.Constants;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServices {

    /**************************************** API Calls - GET ************************************/

    @GET(Constants.APIEndPoints.API_LOGIN)
    Observable<Response<AbstractResponse>> login(@Query("username") String username,
                                                 @Query("password") String password);


    /**************************************** API Calls - POST ************************************/

    @POST(Constants.APIEndPoints.API_SIGN_UP)
    Observable<Response<AbstractResponse>> signUp(@Body SignUpRequest request);
}