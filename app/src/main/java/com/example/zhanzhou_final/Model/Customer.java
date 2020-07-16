package com.example.zhanzhou_final.Model;

import java.io.Serializable;

public class Customer implements Comparable, Serializable {
    private String name;
    private String family;
    private String phone;
    private String sin;
    private Account account;

    public Customer() {
    }

    public Customer(String name, String family, String phone, String sin, Account account) {
        this.name = name;
        this.family = family;
        this.phone = phone;
        this.sin = sin;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name + " "+family+"\n" + "Account No. " + account.getAccountNum();
    }

    @Override
    public int compareTo(Object o) {
        Customer otherCustomer = (Customer) o;
        return this.family.compareTo(otherCustomer.family);
    }
}
