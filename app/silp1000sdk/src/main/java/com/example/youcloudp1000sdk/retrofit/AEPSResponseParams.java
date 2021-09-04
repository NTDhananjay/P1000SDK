package com.example.youcloudp1000sdk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AEPSResponseParams {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("respcode")
    @Expose
    private String respcode;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("aepsTransaction")
    @Expose
    private AepsTransaction aepsTransaction;
    @SerializedName("aepsTransactionSbm")
    @Expose
    private AepsTransactionSbm aepsTransactionSbm;
    @SerializedName("codeValues")
    @Expose
    private List<CodeValue> codeValues = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AepsTransaction getAepsTransaction() {
        return aepsTransaction;
    }

    public void setAepsTransaction(AepsTransaction aepsTransaction) {
        this.aepsTransaction = aepsTransaction;
    }

    public AepsTransactionSbm getAepsTransactionSbm() {
        return aepsTransactionSbm;
    }

    public void setAepsTransactionSbm(AepsTransactionSbm aepsTransactionSbm) {
        this.aepsTransactionSbm = aepsTransactionSbm;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }


    @Override
    public String toString() {
        return "AEPSResponseParams{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", respcode='" + respcode + '\'' +
                ", balance='" + balance + '\'' +
                ", data=" + data +
                ", aepsTransaction=" + aepsTransaction +
                ", aepsTransactionSbm=" + aepsTransactionSbm +
                ", codeValues=" + codeValues +
                '}';
    }
}
