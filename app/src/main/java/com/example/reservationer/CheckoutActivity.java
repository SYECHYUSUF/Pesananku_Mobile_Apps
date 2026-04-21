package com.example.reservationer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class CheckoutActivity extends AppCompatActivity {

    private String menu, size, topping, tableNo;
    private String[] paymentMethods = {"Cash", "QRIS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        menu = getIntent().getStringExtra("MENU");
        size = getIntent().getStringExtra("SIZE");
        topping = getIntent().getStringExtra("TOPPING");
        tableNo = getIntent().getStringExtra("TABLE_NUMBER");

        EditText etName = findViewById(R.id.et_name);
        Spinner spPayment = findViewById(R.id.sp_payment);
        Button btnPay = findViewById(R.id.btn_pay);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPayment.setAdapter(adapter);

        btnPay.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String payment = spPayment.getSelectedItem().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "Masukkan nama pemesan", Toast.LENGTH_SHORT).show();
                return;
            }

            String receiptData = "Ringkasan Pesanan\n" +
                    "------------------\n" +
                    "Meja: " + tableNo + "\n" +
                    "Nama: " + name + "\n" +
                    "Menu: " + menu + " (" + size + ")\n" +
                    "Ekstra Topping: " + topping + "\n" +
                    "Pembayaran: " + payment + "\n" +
                    "Status: LUNAS";

            saveToFile(receiptData);
        });
    }

    private void saveToFile(String data) {
        String filename = "receipt.txt";
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Intent intent = new Intent(CheckoutActivity.this, ReceiptActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }
}