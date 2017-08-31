package com.suparat.apisit.sccexecutivesummary.model;

/**
 * Created by Apisit on 31/08/2560.
 */

public class SP_WEB_RP_BEST_SELLER {
    String BRAND_ID;
    String MODEL_ID;
    String BRAND_NAME;
    String MODEL_NAME;
    int QTY_SALE;
    int QTY_REMAIN;

    public String getBRAND_ID() {
        return BRAND_ID;
    }

    public void setBRAND_ID(String BRAND_ID) {
        this.BRAND_ID = BRAND_ID;
    }

    public String getMODEL_ID() {
        return MODEL_ID;
    }

    public void setMODEL_ID(String MODEL_ID) {
        this.MODEL_ID = MODEL_ID;
    }

    public String getBRAND_NAME() {
        return BRAND_NAME;
    }

    public void setBRAND_NAME(String BRAND_NAME) {
        this.BRAND_NAME = BRAND_NAME;
    }

    public String getMODEL_NAME() {
        return MODEL_NAME;
    }

    public void setMODEL_NAME(String MODEL_NAME) {
        this.MODEL_NAME = MODEL_NAME;
    }

    public int getQTY_SALE() {
        return QTY_SALE;
    }

    public void setQTY_SALE(int QTY_SALE) {
        this.QTY_SALE = QTY_SALE;
    }

    public int getQTY_REMAIN() {
        return QTY_REMAIN;
    }

    public void setQTY_REMAIN(int QTY_REMAIN) {
        this.QTY_REMAIN = QTY_REMAIN;
    }
}
