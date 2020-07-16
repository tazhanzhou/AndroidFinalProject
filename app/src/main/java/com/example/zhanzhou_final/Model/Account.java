package com.example.zhanzhou_final.Model;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private int accountNum;
    private String openDate;
    private double balance;

    public Account(int accountNum, String openDate, double balance) {
        this.accountNum = accountNum;
        this.openDate = openDate;
        this.balance = balance;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
