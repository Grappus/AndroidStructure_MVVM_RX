package com.grappus.android.basemvvm.controllers.network;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.grappus.android.basemvvm.BuildConfig;
import com.grappus.android.basemvvm.R;
import com.grappus.android.basemvvm.controllers.network.models.request.SignUpRequest;
import com.grappus.android.basemvvm.controllers.network.models.response.AbstractResponse;
import com.grappus.android.basemvvm.listeners.APIResponseListener;
import com.grappus.android.basemvvm.repositories.BaseRepository;
import com.grappus.android.basemvvm.repositories.LoginSignUpRepository;
import com.grappus.android.basemvvm.utils.AppLog;
import com.grappus.android.basemvvm.utils.Constants;
import com.grappus.android.basemvvm.utils.Utils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chandrapratapsingh on 6/8/18.
 */

public class APIController implements Constants.LocalBroadcastAction, Constants.Global,
        Constants.APIRequestFragments, Constants.APIResponseStatus , Constants.APIEndPoints{

    private final String TAG = APIController.class.getSimpleName();

    private static Context context;
    private static APIController apiController;

    private Retrofit retrofit;
    public APIServices apiServices;


    private APIController() {
        prepareRequest();
    }

    private APIController(String baseUrl) {
        prepareRequest(baseUrl);
    }


    /********************************** Initialize API Controller Instance ***********************/
    public static APIController getInstance(Context mCtx) {
        context = mCtx;
        if (apiController == null) apiController = new APIController();
        return apiController;
    }

    public static APIController getInstance(Context mCtx, String baseUrl) {
        context = mCtx;
        apiController = null;
        return new APIController(baseUrl);
    }


    /********************************** Prepare Retrofit Instance ********************************/
    private void prepareRequest() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // set connection time out
        httpClient.connectTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS);
        // add your other interceptors … set custom headers
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();

            builder.addHeader(HEADER_CONTENT_TYPE, RESPONSE_TYPE_JSON);
            builder.addHeader(HEADER_DEVICE_TYPE, DEVICE_TYPE);
            builder.addHeader(HEADER_APP_VERSION, BuildConfig.VERSION_NAME);

            builder.method(original.method(), original.body());
            Request request = builder.build();
            return chain.proceed(request);
        });
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        apiServices = retrofit.create(APIServices.class);
    }

    private void prepareRequest(String baseUrl) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        // set connection time out
        httpClient.connectTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        // add your other interceptors … set custom headers
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();

                builder.addHeader(HEADER_CONTENT_TYPE, RESPONSE_TYPE_JSON);
                builder.addHeader(HEADER_DEVICE_TYPE, DEVICE_TYPE);
                builder.addHeader(HEADER_APP_VERSION, BuildConfig.VERSION_NAME);

                builder.method(original.method(), original.body());
                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        apiServices = retrofit.create(APIServices.class);
    }


    /******************************** Prepare Retrofit Request ***********************************/
    private <T> void retrofitRequest(final String endPoint, final Call<T> retroFitRequest,
                                     final APIResponseListener mListener) {

        if (!Utils.isInternetConnected(context)) {
            if (mListener != null)
                mListener.onError(endPoint, 0, context.getString(R.string.error_internet));
            return;
        }

        retroFitRequest.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                handleResponse(response, mListener, endPoint);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                AppLog.e(TAG, "API Failure: " + t.getMessage());
                mListener.onError(endPoint, 0, t.getMessage());
            }
        });
    }

    //Observable Request using RxJava
    private <T> Disposable retrofitObservableRequest(Observable<Response<T>> observable,
                                                     String endPoint, BaseRepository repository) {

        if (!Utils.isInternetConnected(context)) {
            return Observable.error(new Throwable())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(res -> {
                    }, error -> {
                        repository.onError(endPoint, ERROR_INTERNET_CONNECTION,
                                context.getString(R.string.error_internet));
                        Utils.showToast(context, context.getString(R.string.error_internet));
                    });
        }

        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    handleObservableResponse(endPoint, repository, response);
                }, error -> {
                    repository.handelError(endPoint, error);
                });
    }


    /******************************* Handle Retrofit Response ************************************/
    private <T> void handleResponse(Response<T> response, APIResponseListener mListener, String endPoint) {
        AppLog.e(TAG, "API Response: " + response.code());

        if (response.isSuccessful()) mListener.onSuccess(endPoint, response.body());
        else {
            AbstractResponse errorResponse = null;
            try {
                if (response.code() == STATUS_INVALID_CREDENTIALS) {
                    Utils.showToast(context, "Your session has expired!");
                    //Send broadcast
                    Intent intent = new Intent(Constants.LocalBroadcastAction.ACTION_LOGOUT);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    return;
                }

                Converter<ResponseBody, AbstractResponse> converter = retrofit.responseBodyConverter
                        (AbstractResponse.class, AbstractResponse.class.getAnnotations());
                errorResponse = converter.convert(response.errorBody());
                if (errorResponse != null) {
                    AppLog.e(TAG, "API Error: " + errorResponse.getStatusCode() + " - " + errorResponse.getMessage());
                    mListener.onError(endPoint, errorResponse.getStatusCode(), errorResponse.getMessage());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mListener.onError(endPoint, response.code(), context.getString(R.string.error_general));
        }
    }

    //Handle Observable Response using RxJava
    private <T> void handleObservableResponse(String endPoint, BaseRepository repository,
                                              Response<T> response) {

        if (response.isSuccessful()) repository.onSuccess(endPoint, response.body());
        else {
            AbstractResponse errorResponse = null;
            try {
                if (response.code() == STATUS_INVALID_CREDENTIALS) {
                    Utils.showToast(context, "Your session has expired!");
                    //Send broadcast
                    Intent intent = new Intent(Constants.LocalBroadcastAction.ACTION_LOGOUT);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    return;
                }

                Converter<ResponseBody, AbstractResponse> converter = retrofit.responseBodyConverter
                        (AbstractResponse.class, AbstractResponse.class.getAnnotations());
                errorResponse = converter.convert(response.errorBody());
                if (errorResponse != null) {
                    AppLog.e(TAG, "API Error: " + errorResponse.getStatusCode()
                            + " - " + errorResponse.getMessage());
                    repository.onError(endPoint, errorResponse.getStatusCode(), errorResponse.getMessage());
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            repository.onError(endPoint, response.code(), context.getString(R.string.error_general));
        }
    }


    /**************************************** API Calls - GET ************************************/

    public Disposable login(String username, String password, LoginSignUpRepository repository) {
        Observable<Response<AbstractResponse>> observable = apiServices.login(username, password);
        return retrofitObservableRequest(observable, API_LOGIN, repository);
    }


    /**************************************** API Calls - POST ************************************/

    public Disposable signUp(SignUpRequest request, LoginSignUpRepository repository) {
        Observable<Response<AbstractResponse>> observable = apiServices.signUp(request);
        return retrofitObservableRequest(observable, API_SIGN_UP, repository);
    }
}