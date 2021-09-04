package com.example.youcloudp1000sdk.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AepsTransactionSbm {

    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("stan")
    @Expose
    private Integer stan;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("msidn")
    @Expose
    private String msidn;
    @SerializedName("aadharno")
    @Expose
    private String aadharno;
    @SerializedName("bankcode")
    @Expose
    private String bankcode;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("charges")
    @Expose
    private Double charges;
    @SerializedName("poslocation")
    @Expose
    private String poslocation;
    @SerializedName("currencycode")
    @Expose
    private Integer currencycode;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("responsecode")
    @Expose
    private String responsecode;
    @SerializedName("responsemsg")
    @Expose
    private String responsemsg;
    @SerializedName("authid")
    @Expose
    private String authid;
    @SerializedName("bankRefNo")
    @Expose
    private String bankRefNo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("matmTid")
    @Expose
    private String matmTid;
    @SerializedName("isOnus")
    @Expose
    private Boolean isOnus;
    @SerializedName("instSettleStatus")
    @Expose
    private String instSettleStatus;
    @SerializedName("instCommSettleStatus")
    @Expose
    private String instCommSettleStatus;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getStan() {
        return stan;
    }

    public void setStan(Integer stan) {
        this.stan = stan;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMsidn() {
        return msidn;
    }

    public void setMsidn(String msidn) {
        this.msidn = msidn;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getcharges() {
        return charges;
    }

    public void setcharges(Double charges) {
        this.charges = charges;
    }

    public String getPoslocation() {
        return poslocation;
    }

    public void setPoslocation(String poslocation) {
        this.poslocation = poslocation;
    }

    public Integer getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(Integer currencycode) {
        this.currencycode = currencycode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsemsg() {
        return responsemsg;
    }

    public void setResponsemsg(String responsemsg) {
        this.responsemsg = responsemsg;
    }

    public String getAuthid() {
        return authid;
    }

    public void setAuthid(String authid) {
        this.authid = authid;
    }

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public String getmatmTid() {
        return matmTid;
    }

    public void setmatmTid(String matmTid) {
        this.matmTid = matmTid;
    }

    public Boolean getIsOnus() {
        return isOnus;
    }

    public void setIsOnus(Boolean isOnus) {
        this.isOnus = isOnus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstSettleStatus() {
        return instSettleStatus;
    }

    public void setInstSettleStatus(String instSettleStatus) {
        this.instSettleStatus = instSettleStatus;
    }

    public String getInstCommSettleStatus() {
        return instCommSettleStatus;
    }

    public void setInstCommSettleStatus(String instCommSettleStatus) {
        this.instCommSettleStatus = instCommSettleStatus;
    }


    @Override
    public String toString() {
        return "AepsTransactionSbm{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", stan=" + stan +
                ", transactionDate='" + transactionDate + '\'' +
                ", msidn='" + msidn + '\'' +
                ", aadharno='" + aadharno + '\'' +
                ", bankcode='" + bankcode + '\'' +
                ", amount=" + amount +
                ", charges=" + charges +
                ", poslocation='" + poslocation + '\'' +
                ", currencycode=" + currencycode +
                ", note='" + note + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", responsecode='" + responsecode + '\'' +
                ", responsemsg='" + responsemsg + '\'' +
                ", authid='" + authid + '\'' +
                ", bankRefNo='" + bankRefNo + '\'' +
                ", status='" + status + '\'' +
                ", matmTid='" + matmTid + '\'' +
                ", isOnus=" + isOnus +
                ", instSettleStatus='" + instSettleStatus + '\'' +
                ", instCommSettleStatus='" + instCommSettleStatus + '\'' +
                '}';
    }
}
