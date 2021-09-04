package com.example.youcloudp1000sdk.model;

/**
 * Created by shankar.savant on 30-05-2018.
 */

public class MDRReportModel {
    //private Date txDate;
    private String txDate;
    //private Date setDate;
    private String setDate;
    private int noOfTx;
    private Double totalTxAmt;
    private Double mdrAmt;
    private Double payoutAmt;

    public MDRReportModel() {
    }
/*    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }*/

    public String getTxDate() {
        return txDate;
    }

    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public int getNoOfTx() {
        return noOfTx;
    }

    public void setNoOfTx(int noOfTx) {
        this.noOfTx = noOfTx;
    }

    public Double getTotalTxAmt() {
        return totalTxAmt;
    }

    public void setTotalTxAmt(Double totalTxAmt) {
        this.totalTxAmt = totalTxAmt;
    }

    public Double getMdrAmt() {
        return mdrAmt;
    }

    public void setMdrAmt(Double mdrAmt) {
        this.mdrAmt = mdrAmt;
    }

    public Double getPayoutAmt() {
        return payoutAmt;
    }

    public void setPayoutAmt(Double payoutAmt) {
        this.payoutAmt = payoutAmt;
    }

    @Override
    public String toString() {
        return "MDRReportModel [txDate=" + txDate + ", setDate=" + setDate + ", noOfTx=" + noOfTx + ", totalTxAmt="
                + totalTxAmt + ", mdrAmt=" + mdrAmt + ", payoutAmt=" + payoutAmt + "]";
    }
}
