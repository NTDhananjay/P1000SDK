package com.example.youcloudp1000sdk.retrofit;


import com.example.youcloudp1000sdk.model.ModelDmtReqParam;

public class RequestParamDmt {
    public String requestcode;
    public String username;
    public String imei;
    public String imsi;
    public String sessionId;
    public String data;
    public ModelDmtReqParam dmtReqParam;

    public String getRequestcode() {
        return requestcode;
    }

    public void setRequestcode(String requestcode) {
        this.requestcode = requestcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ModelDmtReqParam getDmtReqParam() {
        return dmtReqParam;
    }

    public void setDmtReqParam(ModelDmtReqParam dmtReqParam) {
        this.dmtReqParam = dmtReqParam;
    }
}