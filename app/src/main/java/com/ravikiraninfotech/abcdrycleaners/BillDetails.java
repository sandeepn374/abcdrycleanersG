package com.ravikiraninfotech.abcdrycleaners;

/**
 * Created by kshravi on 05/11/2017 AD.
 */

public class BillDetails {


    String clothType;
    String remark;
    String qty;
    String price;

    public BillDetails(){}
    public BillDetails(String clothType, String price, String remark, String qty){
        this.clothType=clothType;
        this.remark=remark;
        this.qty=qty;
        this.price=price;


    }

    public String getClothType(){

        return this.clothType;
    }
    public String getRemark(){
        return this.remark;
    }

    public String getQty(){
        return this.qty;
    }
    public String getPrice(){
        return this.price;
    }


}
