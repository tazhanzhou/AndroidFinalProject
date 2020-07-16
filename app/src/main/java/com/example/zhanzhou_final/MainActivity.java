package com.example.zhanzhou_final;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zhanzhou_final.Model.BankDB;
import com.example.zhanzhou_final.Model.Customer;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    Button btnAddNewCustomer;
    public final static BankDB bankDB = new BankDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        listView = findViewById(R.id.listView);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bundleExtra",bankDB.customers.get(position));

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("intentExtraDetail", bundle);
//should make withdraw class get the intent bundle.
                        startActivity(intent);
                    }
                };
        listView.setOnItemClickListener(itemClickListener);

        btnAddNewCustomer = findViewById(R.id.btnAddNewCustomer);
        btnAddNewCustomer.setOnClickListener(this);

        showDataInListView(bankDB.customers);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundleExtra",bankDB.customers.get(position));

                Intent intent = new Intent(MainActivity.this, WithdrawActivity.class);
                intent.putExtra("intentExtraWithdraw", bundle);

                startActivity(intent);
                return true;
            }
        });
    }

    private void showDataInListView(ArrayList<Customer> customers) {
        Collections.sort(customers);
        String[] data = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            data[i] = customers.get(i).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }

    private void goAddNewCustomer() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("intentExtraCreate",1);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        goAddNewCustomer();
    }

}