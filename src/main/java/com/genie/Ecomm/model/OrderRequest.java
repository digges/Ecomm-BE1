package com.genie.Ecomm.model;

import java.util.Map;

public class OrderRequest {

    private Map<Long,Integer> productsquantity;
    private double totalamt;

    public Map<Long, Integer> getProductsquantity() {
        return productsquantity;
    }

    public void setProductsquantity(Map<Long, Integer> productsquantity) {
        this.productsquantity = productsquantity;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
    }
}
