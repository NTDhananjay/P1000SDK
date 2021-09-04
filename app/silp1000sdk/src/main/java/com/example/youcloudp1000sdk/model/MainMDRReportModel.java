package com.example.youcloudp1000sdk.model;

import java.util.ArrayList;

/**
 * Created by shankar.savant on 30-05-2018.
 */

public class MainMDRReportModel {
    private ArrayList<MDRReportModel> cardTx;
    private ArrayList<MDRReportModel> voidTx;
    private ArrayList<MDRReportModel> upiTx;

    public MainMDRReportModel() {
    }

    public MainMDRReportModel(ArrayList<MDRReportModel> cardTx, ArrayList<MDRReportModel> voidTx, ArrayList<MDRReportModel> upiTx) {
        this.cardTx = cardTx;
        this.voidTx = voidTx;
        this.upiTx = upiTx;
    }

    public ArrayList<MDRReportModel> getCardTx() {
        return cardTx;
    }

    public void setCardTx(ArrayList<MDRReportModel> cardTx) {
        this.cardTx = cardTx;
    }

    public ArrayList<MDRReportModel> getVoidTx() {
        return voidTx;
    }

    public void setVoidTx(ArrayList<MDRReportModel> voidTx) {
        this.voidTx = voidTx;
    }

    public ArrayList<MDRReportModel> getUpiTx() {
        return upiTx;
    }

    public void setUpiTx(ArrayList<MDRReportModel> upiTx) {
        this.upiTx = upiTx;
    }
}
