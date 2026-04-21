package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailOrderActivity extends AppCompatActivity {

    private String selectedMenu, tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        selectedMenu = getIntent().getStringExtra("SELECTED_MENU");
        tableNo = getIntent().getStringExtra("TABLE_NUMBER");

        TextView tvSelectedMenu = findViewById(R.id.tv_selected_menu);
        tvSelectedMenu.setText("Menu: " + selectedMenu);

        RadioGroup rgSize = findViewById(R.id.rg_size);
        CheckBox cbExtraTopping = findViewById(R.id.cb_extra_topping);
        Button btnAddToCart = findViewById(R.id.btn_add_to_cart);

        btnAddToCart.setOnClickListener(v -> {
            int selectedId = rgSize.getCheckedRadioButtonId();
            RadioButton rbSelected = findViewById(selectedId);
            String size = rbSelected.getText().toString();
            String topping = cbExtraTopping.isChecked() ? "Ya" : "Tidak";

            Intent intent = new Intent(DetailOrderActivity.this, CheckoutActivity.class);
            intent.putExtra("MENU", selectedMenu);
            intent.putExtra("SIZE", size);
            intent.putExtra("TOPPING", topping);
            intent.putExtra("TABLE_NUMBER", tableNo);
            startActivity(intent);
        });
    }
}