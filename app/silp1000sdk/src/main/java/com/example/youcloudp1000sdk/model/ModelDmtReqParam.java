package com.example.youcloudp1000sdk.model;

import java.io.Serializable;

public class ModelDmtReqParam implements Serializable {
    public String amount;
    public String agentId;
    public String custMobile;
    public String referenceNo;
    public String beneMobNo;
    public String custLastName;
    public String beneIfsc;
    public String benName;
    public String bankName;
    public String custPincode;
    public String custFirstName;
    public String custAddress;
    public String beneAccNo;
    public String stateCode;
    public String custDob;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getBeneMobNo() {
        return beneMobNo;
    }

    public void setBeneMobNo(String beneMobNo) {
        this.beneMobNo = beneMobNo;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public String getBeneIfsc() {
        return beneIfsc;
    }

    public void setBeneIfsc(String beneIfsc) {
        this.beneIfsc = beneIfsc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustPincode() {
        return custPincode;
    }

    public void setCustPincode(String custPincode) {
        this.custPincode = custPincode;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getBeneAccNo() {
        return beneAccNo;
    }

    public void setBeneAccNo(String beneAccNo) {
        this.beneAccNo = beneAccNo;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCustDob() {
        return custDob;
    }

    public void setCustDob(String custDob) {
        this.custDob = custDob;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    @Override
    public String toString() {
        return "ModelDmtReqParam{" +
                "amount='" + amount + '\'' +
                ", agentId='" + agentId + '\'' +
                ", custMobile='" + custMobile + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", beneMobNo='" + beneMobNo + '\'' +
                ", custLastName='" + custLastName + '\'' +
                ", beneIfsc='" + beneIfsc + '\'' +
                ", benName='" + benName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", custPincode='" + custPincode + '\'' +
                ", custFirstName='" + custFirstName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", beneAccNo='" + beneAccNo + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", custDob='" + custDob + '\'' +
                '}';
    }

    public ModelDmtReqParam() {

    }

    public ModelDmtReqParam(ModelDmtReqParam sender, ModelDmtReqParam receiver, String amount) {
        this.amount = amount;

        custMobile = sender.getCustMobile();
        custFirstName = sender.getCustFirstName();
        custLastName = sender.getCustLastName();
        custPincode = sender.getCustPincode();
        custAddress = sender.getCustAddress();
        stateCode = sender.getStateCode();
        custDob = sender.getCustDob();

        beneIfsc = receiver.getBeneIfsc();
        benName = receiver.getBenName();
        bankName = receiver.getBankName();
        beneMobNo = receiver.getBeneMobNo();
        beneAccNo = receiver.getBeneAccNo();
    }

}
