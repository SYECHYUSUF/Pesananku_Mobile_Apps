package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

public class DetailOrderActivity extends AppCompatActivity {

    private String selectedMenu, tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        selectedMenu = getIntent().getStringExtra("SELECTED_MENU");
        tableNo = getIntent().getStringExtra("TABLE_NUMBER");

        ImageView ivImage = findViewById(R.id.iv_detail_image);
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvPrice = findViewById(R.id.tv_detail_price);

        tvName.setText(selectedMenu);
        
        if (selectedMenu.equals("Kopi Susu")) {
            ivImage.setImageResource(R.drawable.kopsu);
            tvPrice.setText("Rp 15.000");
        } else if (selectedMenu.equals("Nasi Goreng")) {
            ivImage.setImageResource(R.drawable.nasgor);
            tvPrice.setText("Rp 25.000");
        } else {
            ivImage.setImageResource(R.drawable.kentang);
            tvPrice.setText("Rp 12.000");
        }

        RadioGroup rgSize = findViewById(R.id.rg_size);
        RadioGroup rgSugar = findViewById(R.id.rg_sugar);
        CheckBox cbCream = findViewById(R.id.cb_cream);
        CheckBox cbTopping = findViewById(R.id.cb_topping);
        CheckBox cbNasi = findViewById(R.id.cb_nasi);
        EditText etNote = findViewById(R.id.et_custom_note);
        Button btnAddMore = findViewById(R.id.btn_add_more);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        btnAddMore.setOnClickListener(v -> {
            Toast.makeText(this, "Pesanan ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnConfirm.setOnClickListener(v -> {
            if (tableNo == null || tableNo.isEmpty()) {
                Toast.makeText(this, "Silakan scan barcode meja Anda terlebih dahulu!", Toast.LENGTH_LONG).show();
                // Jika meja belum di-scan, kita bisa arahkan balik ke Home atau paksa scan di sini
                // Namun sesuai permintaan, tombol scan ada di bottom bar Home. 
                // Kita bisa finish() agar user balik ke Home untuk scan.
                return;
            }

            int sizeId = rgSize.getCheckedRadioButtonId();
            RadioButton rbSize = findViewById(sizeId);
            String size = rbSize.getText().toString();

            int sugarId = rgSugar.getCheckedRadioButtonId();
            RadioButton rbSugar = findViewById(sugarId);
            String sugar = rbSugar.getText().toString();
            
            StringBuilder addons = new StringBuilder();
            if (cbCream.isChecked()) addons.append("Extra Cream, ");
            if (cbTopping.isChecked()) addons.append("Extra Topping, ");
            if (cbNasi.isChecked()) addons.append("Extra Nasi");

            String note = etNote.getText().toString();

            Intent intent = new Intent(DetailOrderActivity.this, CheckoutActivity.class);
            intent.putExtra("MENU", selectedMenu);
            intent.putExtra("SIZE", size);
            intent.putExtra("SUGAR", sugar);
            intent.putExtra("ADDONS", addons.toString());
            intent.putExtra("NOTE", note);
            intent.putExtra("TABLE_NUMBER", tableNo);
            startActivity(intent);
        });
    }
}