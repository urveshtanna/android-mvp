package com.urvesh.android_arch_mvp.domain.model;

import java.util.HashMap;

public class RequestUrl {

    private String accessToken;
    private HashMap<String, String> additionalParams;
    private boolean isDebug;
    private String urlPath;
    private String title;
    private boolean externalLink;

    public RequestUrl(boolean isDebug, HashMap<String, String> additionalParams) {
        this.isDebug = isDebug;
        this.additionalParams = additionalParams;
    }

    public RequestUrl(String urlPath) {
        this.urlPath = urlPath;
    }

    public RequestUrl(boolean isDebug, String accessToken, HashMap<String, String> additionParams) {
        this.isDebug = isDebug;
        this.accessToken = accessToken;
        this.additionalParams = additionParams;
    }

    public String getAccessToken() {
        return "Token token=" + accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAdditionParams(HashMap<String, String> additionParams) {
        this.additionalParams = additionParams;
    }

    public HashMap<String, String> getAdditionalParams() {
        return additionalParams;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExternalLink() {
        return externalLink;
    }

    public void setExternalLink(boolean externalLink) {
        this.externalLink = externalLink;
    }
}
