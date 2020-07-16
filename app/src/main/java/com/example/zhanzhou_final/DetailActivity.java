package com.example.zhanzhou_final;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zhanzhou_final.Model.Account;
import com.example.zhanzhou_final.Model.Customer;

import java.io.File;
import java.io.Serializable;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextAccountNum, editTextOpenDate, editTextBalance, editTextName, editTextFamily, editTextPhone, editTextSIN;
    Button btnAdd, btnFind, btnRemove, btnUpdate, btnSave, btnLoad, btnClear, btnShowAll;
    File file_object_private_external_storage;
    int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initialize();
        createFileInExternalStoragePrivateDirectory();
    }

    private void initialize() {
        editTextAccountNum = findViewById(R.id.editTextAccountNumber);
        editTextOpenDate = findViewById(R.id.editTextOpenDate);
        editTextBalance = findViewById(R.id.editTextBalance);
        editTextName = findViewById(R.id.editTextTextPersonName);
        editTextFamily = findViewById(R.id.editTextTextFamily);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextSIN = findViewById(R.id.editTextSIN);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnFind = findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);

        btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnShowAll = findViewById(R.id.btnShowAll);
        btnShowAll.setOnClickListener(this);

        showDetail();
    }

    private void showDetail() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getBundleExtra("intentExtraDetail") != null) {
                Bundle bundle = intent.getBundleExtra("intentExtraDetail");
                Serializable bundledCustomer = bundle.getSerializable("bundleExtra");
                fromObjectToTable((Customer) bundledCustomer);
            }

        } else return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                if (requiredFieldValidator() == false) {
                    MainActivity.bankDB.customers.add(createCustomer());
                    showAll();
                }
                break;
            case R.id.btnFind:
                if (TextUtils.isEmpty(editTextSIN.getText())) {
                    Toast.makeText(this, "Unvalidated Input", Toast.LENGTH_SHORT).show();
                } else if (findCustomerBySIN(editTextSIN.getText().toString()) != null) {
                    Customer c = findCustomerBySIN(editTextSIN.getText().toString());
                    fromObjectToTable(c);
                }
                break;
            case R.id.btnRemove:
                if (removeCustomerBySIN(editTextSIN.getText().toString())) {
                    clear();
                } else return;
                break;
            case R.id.btnUpdate:
                updateCustomerBySIN(editTextSIN.getText().toString());
                clear();
                break;
            case R.id.btnClear:
                clear();
                break;
            case R.id.btnShowAll:
                showAll();
                break;
            case R.id.btnSave:
                if (requiredFieldValidator() == false) {
                    Customer customer = createCustomer();
                    FileManager.saveObject(file_object_private_external_storage,
                            customer);
                    clear();
                }
                break;
            case R.id.btnLoad:
                Customer customer = (Customer) FileManager.loadObject(file_object_private_external_storage);
                fromObjectToTable(customer);
                break;
        }
    }

    private Customer createCustomer() {
        Account account = new Account(
                Integer.parseInt(editTextAccountNum.getText().toString()),
                editTextOpenDate.getText().toString(),
                Double.parseDouble(editTextBalance.getText().toString())
        );
        Customer customer = new Customer(
                editTextName.getText().toString(),
                editTextFamily.getText().toString(),
                editTextPhone.getText().toString(),
                editTextSIN.getText().toString(),
                account
        );
        return customer;
    }

    private Customer findCustomerBySIN(String sin) {
        Customer customer = new Customer();
        boolean isCustomerExist = false;
        for (Customer c :
                MainActivity.bankDB.customers) {
            if (c.getSin().equals(sin)) {
                customer = c;
                isCustomerExist = true;
            }
        }
        if (isCustomerExist == false) {
            Toast.makeText(this, "No Qualified Customer founded", Toast.LENGTH_SHORT).show();
            customer = null;
        }
        return customer;
    }

    private boolean removeCustomerBySIN(String sin) {
        boolean isCustomerDeleted = false;
        for (Customer c :
                MainActivity.bankDB.customers) {
            if (c.getSin().equals(sin)) {
                MainActivity.bankDB.customers.remove(c);
                isCustomerDeleted = true;
                break;
            }
        }
        if (isCustomerDeleted == false) {
            Toast.makeText(this, "No Qualified Customer founded", Toast.LENGTH_SHORT).show();
        }
        return isCustomerDeleted;
    }

    private void showAll() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean updateCustomerBySIN(String sin) {
        boolean isCustomerUpdated = false;

        for (Customer c :
                MainActivity.bankDB.customers) {
            if (c.getSin().equals(sin)) {
                fromTableToObject(c);
                isCustomerUpdated = true;
            }
        }
        if (isCustomerUpdated == false) {
            Toast.makeText(this, "No Qualified Customer founded", Toast.LENGTH_SHORT).show();
        }
        return isCustomerUpdated;
    }

    // Fetch Customer Information from object and display
    private void fromObjectToTable(Customer c) {
        editTextAccountNum.setText(String.valueOf(c.getAccount().getAccountNum()));
        editTextOpenDate.setText(c.getAccount().getOpenDate());
        editTextBalance.setText(String.valueOf(c.getAccount().getBalance()));

        editTextName.setText(c.getName());
        editTextFamily.setText(c.getFamily());
        editTextPhone.setText(c.getPhone());
        editTextSIN.setText(c.getSin());
    }

    // Put page information into Object
    private void fromTableToObject(Customer c) {
        c.getAccount().setAccountNum(Integer.parseInt(editTextAccountNum.getText().toString()));
        c.getAccount().setOpenDate(editTextOpenDate.getText().toString());
        c.getAccount().setBalance(Double.parseDouble(editTextBalance.getText().toString()));

        c.setName(editTextName.getText().toString());
        c.setFamily(editTextFamily.getText().toString());
        c.setPhone(editTextPhone.getText().toString());
        c.setSin(editTextSIN.getText().toString());
    }

    // Check if all the field is filled
    private boolean requiredFieldValidator() {
        boolean isAnyBlank = false;
        if (TextUtils.isEmpty(editTextAccountNum.getText())) {
            Toast.makeText(this, "Account Number Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextOpenDate.getText())) {
            Toast.makeText(this, "Open Date Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextBalance.getText())) {
            Toast.makeText(this, "Balance Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextName.getText())) {
            Toast.makeText(this, "Name Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextFamily.getText())) {
            Toast.makeText(this, "Family Name Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextPhone.getText())) {
            Toast.makeText(this, "Phone Number Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        if (TextUtils.isEmpty(editTextSIN.getText())) {
            Toast.makeText(this, "SIN Required", Toast.LENGTH_SHORT).show();
            isAnyBlank = true;
        }
        return isAnyBlank;
    }

    // Clear all fields
    private void clear() {
        editTextAccountNum.setText("");
        editTextOpenDate.setText("");
        editTextBalance.setText("");
        editTextName.setText("");
        editTextFamily.setText("");
        editTextPhone.setText("");
        editTextSIN.setText("");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this,
                        "Permission Denied",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Permission Granted",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createFileInExternalStoragePrivateDirectory() {

        file_object_private_external_storage = FileManager.createFile(getExternalFilesDir("private_file"),
                "file_object_private_external_storage");
    }
}