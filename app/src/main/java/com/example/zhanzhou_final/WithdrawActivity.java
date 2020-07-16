
package com.example.zhanzhou_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zhanzhou_final.Model.Account;
import com.example.zhanzhou_final.Model.Customer;

import java.io.Serializable;

public class WithdrawActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewBalance;
    EditText editTextAmount;
    Button btnWithdraw;

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        initialize();
        showBalance();
    }

    private void initialize() {
        textViewBalance = findViewById(R.id.textViewBalance);
        editTextAmount = findViewById(R.id.editTextAmount);

        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnWithdraw.setOnClickListener(this);
    }

    private void showBalance() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getBundleExtra("intentExtraWithdraw") != null) {
                Bundle bundle = intent.getBundleExtra("intentExtraWithdraw");
                Serializable bundledCustomer = bundle.getSerializable("bundleExtra");

                account = ((Customer) bundledCustomer).getAccount();
                textViewBalance.setText(String.valueOf(account.getBalance()));
            }

        } else return;
    }

    @Override
    public void onClick(View v) {
        updateDB(account.getAccountNum());
    }

    private void withdraw(Account account) {
        double withdrawAmount = Double.parseDouble(editTextAmount.getText().toString());
        if (withdrawAmount < 0 || TextUtils.isEmpty(editTextAmount.getText())) {
            Toast.makeText(this, "Unvalidated Input", Toast.LENGTH_SHORT).show();
        } else if (withdrawAmount > account.getBalance()) {
            Toast.makeText(this, "Insufficient Fund", Toast.LENGTH_SHORT).show();
        } else {
            account.setBalance(account.getBalance() - withdrawAmount);
            textViewBalance.setText(String.valueOf(account.getBalance()));
            editTextAmount.setText("");
        }
    }

    private void updateDB(int accountNum) {
        for (Customer c :
                MainActivity.bankDB.customers) {
            if (c.getAccount().getAccountNum() == accountNum) {
                withdraw(c.getAccount());
            }
        }
    }
}