package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

        ImageView ivMenu = findViewById(R.id.iv_checkout_menu);
        TextView tvMenu = findViewById(R.id.tv_summary_menu);
        TextView tvDetails = findViewById(R.id.tv_summary_details);
        TextView tvTable = findViewById(R.id.tv_summary_table);
        
        RadioGroup rgPayment = findViewById(R.id.rg_payment_method);
        RadioGroup rgBankSelection = findViewById(R.id.rg_bank_selection);
        RadioButton rbTransfer = findViewById(R.id.rb_transfer);
        
        Button btnPay = findViewById(R.id.btn_pay_now);
        FrameLayout flLoading = findViewById(R.id.fl_loading);

        // Set Gambar Makanan
        if (menu != null) {
            if (menu.equals("Kopi Susu")) {
                ivMenu.setImageResource(R.drawable.kopsu);
            } else if (menu.equals("Nasi Goreng")) {
                ivMenu.setImageResource(R.drawable.nasgor);
            } else {
                ivMenu.setImageResource(R.drawable.kentang);
            }
        }

        tvMenu.setText("Menu: " + menu);
        tvDetails.setText("Sugar: " + sugar + (addons != null && addons.isEmpty() ? "" : " | Add-ons: " + addons));
        tvTable.setText("Meja: " + tableNo);

        // Logika Pilihan Bank
        rgPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_transfer) {
                rgBankSelection.setVisibility(View.VISIBLE);
            } else {
                rgBankSelection.setVisibility(View.GONE);
                rgBankSelection.clearCheck();
            }
        });

        btnPay.setOnClickListener(v -> {
            int selectedId = rgPayment.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rbPayment = findViewById(selectedId);
            String paymentMethod = rbPayment.getText().toString();

            if (selectedId == R.id.rb_transfer) {
                int bankId = rgBankSelection.getCheckedRadioButtonId();
                if (bankId == -1) {
                    Toast.makeText(this, "Pilih Bank terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton rbBank = findViewById(bankId);
                paymentMethod += " (" + rbBank.getText().toString() + ")";
            }

            String finalPaymentMethod = paymentMethod;
            flLoading.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                flLoading.setVisibility(View.GONE);
                
                // Simpan ke riwayat
                String currentDate = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());
                HistoryActivity.addHistory(menu, tableNo, currentDate);

                Toast.makeText(this, "Pembayaran Berhasil via " + finalPaymentMethod, Toast.LENGTH_LONG).show();
                
                Intent intent = new Intent(CheckoutActivity.this, ReceiptActivity.class);
                intent.putExtra("MENU", menu);
                intent.putExtra("TABLE", tableNo);
                intent.putExtra("PAYMENT", finalPaymentMethod);
                startActivity(intent);
                finish();
            }, 3000);
        });
    }
}