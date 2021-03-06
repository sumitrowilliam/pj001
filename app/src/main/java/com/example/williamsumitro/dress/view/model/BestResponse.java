package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/17/2018.
 */

public class BestResponse implements Serializable{
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("product_info")
    @Expose
    private ArrayList<ProductInfo> productInfo = null;
    @SerializedName("result")
    @Expose
    private ArrayList<ProductInfo> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ArrayList<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }

    public ArrayList<ProductInfo> getResult() {
        return result;
    }

    public void setResult(ArrayList<ProductInfo> result) {
        this.result = result;
    }
}
