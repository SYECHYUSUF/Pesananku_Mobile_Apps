package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private String menu, sugar, addons, tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        menu = getIntent().getStringExtra("MENU");
        sugar = getIntent().getStringExtra("SUGAR");
        addons = getIntent().getStringExtra("ADDONS");
        tableNo = getIntent().getStringExtra("TABLE_NUMBER");

        TextView tvMenu = findViewById(R.id.tv_summary_menu);
        TextView tvDetails = findViewById(R.id.tv_summary_details);
        TextView tvTable = findViewById(R.id.tv_summary_table);
        RadioGroup rgPayment = findViewById(R.id.rg_payment_method);
        Button btnPay = findViewById(R.id.btn_pay_now);
        FrameLayout flLoading = findViewById(R.id.fl_loading);

        tvMenu.setText("Menu: " + menu);
        tvDetails.setText("Sugar: " + sugar + (addons.isEmpty() ? "" : " | Add-ons: " + addons));
        tvTable.setText("Meja: " + tableNo);

        btnPay.setOnClickListener(v -> {
            int selectedId = rgPayment.getCheckedRadioButtonId();
            RadioButton rbPayment = findViewById(selectedId);
            String paymentMethod = rbPayment.getText().toString();

            flLoading.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                flLoading.setVisibility(View.GONE);
                
                // Simpan ke riwayat
                String currentDate = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());
                HistoryActivity.addHistory(menu, tableNo, currentDate);

                Toast.makeText(this, "Pembayaran Berhasil via " + paymentMethod, Toast.LENGTH_LONG).show();
                
                Intent intent = new Intent(CheckoutActivity.this, ReceiptActivity.class);
                intent.putExtra("MENU", menu);
                intent.putExtra("TABLE", tableNo);
                intent.putExtra("PAYMENT", paymentMethod);
                startActivity(intent);
                finish();
            }, 3000);
        });
    }
}