package com.development.allanproject.model.taxholding;

import java.util.ArrayList;

public class GetTaxHolding {

    private Additional additional;
    private int code;
    private ArrayList<TaxData> data;
    private String status;
    private Boolean success;

    public Additional getAdditional() {
        return additional;
    }

    public void setAdditional(Additional additional) {
        this.additional = additional;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<TaxData> getData() {
        return data;
    }

    public void setData(ArrayList<TaxData> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
