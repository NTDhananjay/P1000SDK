package com.example.youcloudp1000sdk.retrofit;

import java.io.Serializable;

public class WalletRequestParam implements Serializable {
    public String requestcode;
    public String username;
    public String imei;
    public String tid;
    public String mid;
    public String imsi;
    public String sessionId;
    public String data;

    public String txnDate;
    public String txnRefNo;
    public String payMode;
    public String txnAmt;    //
    public String bankName;
    //public File file;        //

    public WalletRequestParam() {
    }

    public WalletRequestParam(String requestcode, String username, String imei, String tid, String mid,
                              String imsi, String sessionId, String data, String txnDate, String txnRefNo,
                              String payMode, String txnAmt, String bankName/*, File file*/) {
        this.requestcode = requestcode;
        this.username = username;
        this.imei = imei;
        this.tid = tid;
        this.mid = mid;
        this.imsi = imsi;
        this.sessionId = sessionId;
        this.data = data;
        this.txnDate = txnDate;
        this.txnRefNo = txnRefNo;
        this.payMode = payMode;
        this.txnAmt = txnAmt;
        this.bankName = bankName;
        //this.file = file;
    }

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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnRefNo() {
        return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
        this.txnRefNo = txnRefNo;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /*public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }*/

    @Override
    public String toString() {
        return "WalletRequestParam{" +
                "requestcode='" + requestcode + '\'' +
                ", username='" + username + '\'' +
                ", imei='" + imei + '\'' +
                ", tid='" + tid + '\'' +
                ", mid='" + mid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", data='" + data + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnRefNo='" + txnRefNo + '\'' +
                ", payMode='" + payMode + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", bankName='" + bankName + '\'' +
                /*", file=" + file +*/
                '}';
    }
}
