package com.example.zhanzhou_final.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BankDB implements Serializable {
    Account account1 = new Account(1001, "20200101", 19);
    Account account2 = new Account(1002, "20200202", 29);
    Account account3 = new Account(1003, "20200303", 39);

    Customer customer1 = new Customer("Bob", "Dylan", "123456", "167890",account1);
    Customer customer2 = new Customer("Freddie", "Mercury", "223456", "267890",account2);
    Customer customer3 = new Customer("Ray", "Charles", "323456", "367890",account3);

    public ArrayList<Customer> customers = new ArrayList<Customer>(
            Arrays.asList(customer1,customer2,customer3)
    );
}
