package com.ravikiraninfotech.abcdrycleaners;

import java.util.ArrayList;

/**
 * Created by kshravi on 01/11/2017 AD.
 */

public class User {

    public String name;
   // public String email;
    public String billNumber;
    public String ph;
   public ArrayList<BillDetails> billDetailsArrayList;
    public int total;
    public int due;
    public int discount;
    public int time;
    public String deliveryDate;
    public String pickUpDate;


    public User() {
    }

    public User(String name, String ph, ArrayList<BillDetails> billDetailsArrayList, String bill, int total, int due, int discount,int time,String del,String pick) {
        this.ph=ph;
        this.name = name;
this.billDetailsArrayList=billDetailsArrayList;

        this.billNumber=bill;
        this.total=total;
        this.due=due;
        this.time= time;
        this.discount=discount;
        this.deliveryDate=del;
        this.pickUpDate=pick;
    }
}
