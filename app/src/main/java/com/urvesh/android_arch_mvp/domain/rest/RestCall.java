package com.urvesh.android_arch_mvp.domain.rest;

import com.squareup.otto.Bus;
import com.urvesh.android_arch_mvp.domain.Constants;
import com.urvesh.android_arch_mvp.domain.model.ErrorModel;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RestCall {

    private static final int TIMEOUT_TIME = 30;

    public <S> S createService(String baseUrl, Class<S> serviceClass, boolean isDebuggable) {
        OkHttpClient client = getClient();
        if (isDebuggable) {
            /*client.interceptors().add(new Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                    final com.squareup.okhttp.Response response = chain.proceed(chain.request());
                    // Do anything with response here
                    System.out.println(response.toString());
                    System.out.println(response.request().toString());
                    return response;
                }
            });*/
            /*client.interceptors().add(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    final okhttp3.Response response = chain.proceed(chain.request());
                    // Do anything with response here
                    System.out.println(response.toString());
                    System.out.println(response.request().toString());
                    return response;
                }
            });*/
        }
        /*retrofit2.Retrofit retrofit = new R.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
                .build();
    }

    protected void onError(Throwable t, String tag, Bus bus) {
        if (t instanceof UnknownHostException) {
            bus.post(new ErrorModel(0, Constants.NO_INTERNET_CONNECTION_MSG, tag));
        } else if (t instanceof ConnectException) {
            bus.post(new ErrorModel(504, Constants.PROBLEM_CONNECTING_MSG, tag));
        } else if (t instanceof TimeoutException) {
            bus.post(new ErrorModel(504, Constants.TIMEOUT_CONNECTION_MSG, tag));
        } else {
            bus.post(new ErrorModel(0, Constants.COMMON_ERROR_MSG, tag, t));
        }
    }


}
