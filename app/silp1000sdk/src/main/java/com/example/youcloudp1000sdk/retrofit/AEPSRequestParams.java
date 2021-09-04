package com.example.youcloudp1000sdk.retrofit;


import com.example.youcloudp1000sdk.model.AepsCaptureResponseModel;

public class AEPSRequestParams {
    private String requestcode;
    private String username;
    private String imei;
    private String imsi;
    private String sessionId;
    private String txType;
    private String aadharNo;
    private String amount;
    private String ifscCode;
    private String data;
    private String stan;
    private String pidData;
    private String location; //latitude|longitude
    private String bankCode; //nbin
    private AepsCaptureResponseModel aepsCaptureResponseModel;

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

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public AepsCaptureResponseModel getAepsCaptureResponseModel() {
        return aepsCaptureResponseModel;
    }

    public void setAepsCaptureResponseModel(AepsCaptureResponseModel aepsCaptureResponseModel) {
        this.aepsCaptureResponseModel = aepsCaptureResponseModel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getPidData() {
        return pidData;
    }

    public void setPidData(String pidData) {
        this.pidData = pidData;
    }

    @Override
    public String toString() {
        return "AEPSRequestParams{" +
                "requestcode='" + requestcode + '\'' +
                ", username='" + username + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", txType='" + txType + '\'' +
                ", aadharNo='" + aadharNo + '\'' +
                ", amount='" + amount + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", data='" + data + '\'' +
                ", stan='" + stan + '\'' +
                ", pidData='" + pidData + '\'' +
                ", location='" + location + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", aepsCaptureResponseModel=" + aepsCaptureResponseModel +
                '}';
    }
}
