package com.urvesh.android_arch_mvp.presenter;

import android.content.Context;

import com.squareup.otto.Bus;
import com.urvesh.android_arch_mvp.domain.analytics.ScreenNames;
import com.urvesh.android_arch_mvp.domain.model.ErrorModel;
import com.urvesh.android_arch_mvp.domain.model.RequestUrl;
import com.urvesh.android_arch_mvp.tools.Logger;
import com.urvesh.android_arch_mvp.tools.SettingsManager;
import com.urvesh.android_arch_mvp.tools.Utils;

public abstract class Presenter<T> {

    private Context mContext;
    private T mView;
    private Bus mBus;
    private String mCurrentScreenNames;
    private RequestUrl requestUrl;
    private boolean isRegister = false;
    private String accessToken;

    public Presenter(Context context, T view, Bus bus, @ScreenNames.ScreenName String screenNames) {
        this.mContext = context;
        this.mView = view;
        this.mBus = bus;
        this.mCurrentScreenNames = screenNames;
        requestUrl = new RequestUrl(SettingsManager.isDebuggable(), Utils.additionParams(context));
        if (getAccessToken() != null) requestUrl.setAccessToken(getAccessToken());
    }


    public String getCurrentVersionCode() {
        return Utils.getCurrentVersionCode(getContext()) + "";
    }


    public void start() {
        if (!isRegister) {
            mBus.register(this);
            isRegister = true;
        }
    }

    public void stop() {
        try {
            if (isRegister) {
                mBus.unregister(this);
                isRegister = false;
            }
        } catch (IllegalArgumentException e) {
            Logger.exception(getClass().getSimpleName(), e);
        }
    }

    public Bus getBus() {
        return mBus;
    }

    public void setBus(Bus bus) {
        this.mBus = bus;
    }

    public T getView() {
        return mView;
    }

    public void setView(T view) {
        this.mView = view;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public RequestUrl getRequestUrl() {
        return requestUrl;
    }


    public void error(ErrorModel errorModel) {
        error(errorModel, true);
    }

    public void error(ErrorModel errorModel, boolean showErrorMsg) {
        if (errorModel == null) return;
        if (showErrorMsg) {
            Utils.displayErrorMsg(getContext(), errorModel);
        }
        if (errorModel.getException() != null) {
            Logger.exception(getClass().getName(), errorModel.getException());
        }
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    public void checkBus() {
        if (!isRegister()) {
            start();
        }
    }

    public String getAccessToken() {
        return accessToken;
    }
}