package com.ayy.MySQL.dao2;

import java.sql.Date;

/**
 * @ ClassName Customer
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/01/2021 21H
 * @ Version 1.0
 */
public class Customer {
    private int cust_id;
    private String cust_name;
    private String email;
    private Date birth;

    public Customer(){}

    public Customer(int cust_id, String cust_name, String email, Date birth) {
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.email = email;
        this.birth = birth;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customer{"+this.cust_id+", "+this.cust_name+", "+this.email+", "+this.birth.toString()+"}";
    }
}
