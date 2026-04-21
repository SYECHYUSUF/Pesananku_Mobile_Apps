package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        
        // Set gambar & harga berdasarkan menu
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

        RadioGroup rgSugar = findViewById(R.id.rg_sugar);
        CheckBox cbCream = findViewById(R.id.cb_cream);
        CheckBox cbTopping = findViewById(R.id.cb_topping);
        Button btnAddMore = findViewById(R.id.btn_add_more);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        btnAddMore.setOnClickListener(v -> {
            Toast.makeText(this, "Pesanan ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke home untuk tambah menu
        });

        btnConfirm.setOnClickListener(v -> {
            int sugarId = rgSugar.getCheckedRadioButtonId();
            RadioButton rbSugar = findViewById(sugarId);
            String sugar = rbSugar.getText().toString();
            
            StringBuilder addons = new StringBuilder();
            if (cbCream.isChecked()) addons.append("Extra Cream, ");
            if (cbTopping.isChecked()) addons.append("Extra Topping");

            Intent intent = new Intent(DetailOrderActivity.this, CheckoutActivity.class);
            intent.putExtra("MENU", selectedMenu);
            intent.putExtra("SUGAR", sugar);
            intent.putExtra("ADDONS", addons.toString());
            intent.putExtra("TABLE_NUMBER", tableNo);
            startActivity(intent);
        });
    }
}