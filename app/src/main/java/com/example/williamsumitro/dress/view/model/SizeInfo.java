package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class SizeInfo implements Serializable {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_size_id")
    @Expose
    private Integer productSizeId;
    @SerializedName("size_name")
    @Expose
    private String sizeName;
    @SerializedName("product_qty")
    @Expose
    private Integer productQty;
    public SizeInfo(Integer productId, Integer productSizeId, String sizeName, Integer productQty){
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.sizeName = sizeName;
        this.productQty = productQty;
    }
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Integer productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }


}
