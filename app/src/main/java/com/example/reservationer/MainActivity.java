package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView tvTableNumber;
    private Button btnNext;
    private String tableNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTableNumber = findViewById(R.id.tv_table_number);
        btnNext = findViewById(R.id.btn_next);
        Button btnScan = findViewById(R.id.btn_scan);

        btnScan.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Scan QR Code Meja");
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
        });

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuListActivity.class);
            intent.putExtra("TABLE_NUMBER", tableNo);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_LONG).show();
            } else {
                tableNo = result.getContents();
                tvTableNumber.setText("Nomor Meja: " + tableNo);
                btnNext.setEnabled(true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}