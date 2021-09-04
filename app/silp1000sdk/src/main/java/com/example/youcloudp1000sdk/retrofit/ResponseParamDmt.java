package com.example.youcloudp1000sdk.retrofit;

import com.example.youcloudp1000sdk.model.ModelDmtReqParam;
import com.example.youcloudp1000sdk.model.ModelDmtRespParam;

import java.io.Serializable;
import java.util.ArrayList;


public class ResponseParamDmt implements Serializable {
    public boolean status;
    public String msg;
    public String respcode;
    public String data;
    public ModelDmtRespParam dmtRespParam;
    public ModelDmtReqParam custDetails;
    public ArrayList<ModelDmtReqParam> benDetails;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ModelDmtRespParam getDmtRespParam() {
        return dmtRespParam;
    }

    public void setDmtRespParam(ModelDmtRespParam dmtRespParam) {
        this.dmtRespParam = dmtRespParam;
    }

    public ModelDmtReqParam getCustDetails() {
        return custDetails;
    }

    public void setCustDetails(ModelDmtReqParam custDetails) {
        this.custDetails = custDetails;
    }

    public ArrayList<ModelDmtReqParam> getBenDetails() {
        return benDetails;
    }

    public void setBenDetails(ArrayList<ModelDmtReqParam> benDetails) {
        this.benDetails = benDetails;
    }

    @Override
    public String toString() {
        return "ResponseParamDmt{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", respcode='" + respcode + '\'' +
                ", data='" + data + '\'' +
                ", dmtRespParam=" + dmtRespParam +
                ", custDetails=" + custDetails +
                ", benDetails=" + benDetails +
                '}';
    }
}
