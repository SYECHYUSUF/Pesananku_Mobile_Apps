package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        String menu = getIntent().getStringExtra("MENU");
        String table = getIntent().getStringExtra("TABLE");
        String payment = getIntent().getStringExtra("PAYMENT");

        TextView tvDetails = findViewById(R.id.tv_receipt_details);
        Button btnBack = findViewById(R.id.btn_back_to_main);

        String receiptInfo = "Detail Pesanan:\n" +
                "• Menu: " + menu + "\n" +
                "• No. Meja: " + table + "\n" +
                "• Pembayaran: " + payment + "\n" +
                "• Status: LUNAS";
        
        tvDetails.setText(receiptInfo);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}