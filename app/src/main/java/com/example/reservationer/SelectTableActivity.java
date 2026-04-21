package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SelectTableActivity extends AppCompatActivity {

    private String menu, sugar, addons, note, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_table);

        // Ambil data pesanan dari intent sebelumnya
        menu = getIntent().getStringExtra("MENU");
        sugar = getIntent().getStringExtra("SUGAR");
        addons = getIntent().getStringExtra("ADDONS");
        note = getIntent().getStringExtra("NOTE");
        size = getIntent().getStringExtra("SIZE");

        // Set listener untuk tombol meja 01-05
        setupTableButtons();

        // Set listener untuk tombol scan
        findViewById(R.id.btn_scan_table).setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Scan Barcode Meja");
            integrator.setOrientationLocked(true);
            integrator.setCaptureActivity(CaptureActivityPortrait.class);
            integrator.initiateScan();
        });
    }

    private void setupTableButtons() {
        int[] buttonIds = {R.id.btn_meja_01, R.id.btn_meja_02, R.id.btn_meja_03, R.id.btn_meja_04, R.id.btn_meja_05};
        for (int id : buttonIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> {
                String tableNo = btn.getText().toString();
                goToCheckout(tableNo);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            goToCheckout(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void goToCheckout(String tableNo) {
        Intent intent = new Intent(SelectTableActivity.this, CheckoutActivity.class);
        intent.putExtra("MENU", menu);
        intent.putExtra("SUGAR", sugar);
        intent.putExtra("ADDONS", addons);
        intent.putExtra("NOTE", note);
        intent.putExtra("SIZE", size);
        intent.putExtra("TABLE_NUMBER", tableNo);
        startActivity(intent);
    }
}