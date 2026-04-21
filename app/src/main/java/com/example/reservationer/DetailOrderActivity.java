package com.example.reservationer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

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
        ImageView btnBack = findViewById(R.id.btn_back_detail);

        RadioButton rbSmall = findViewById(R.id.rb_small);
        RadioButton rbMedium = findViewById(R.id.rb_medium);
        RadioButton rbLarge = findViewById(R.id.rb_large);

        tvName.setText(selectedMenu);
        
        if (selectedMenu.equals("Kopi Susu")) {
            ivImage.setImageResource(R.drawable.kopsu);
            tvPrice.setText("Rp 15.000");
            
            setSmallIcon(rbSmall, R.drawable.small);
            setSmallIcon(rbMedium, R.drawable.medium);
            setSmallIcon(rbLarge, R.drawable.large);
        } else if (selectedMenu.equals("Nasi Goreng")) {
            ivImage.setImageResource(R.drawable.nasgor);
            tvPrice.setText("Rp 25.000");
            
            setSmallIcon(rbSmall, R.drawable.small01);
            setSmallIcon(rbMedium, R.drawable.medium02);
            setSmallIcon(rbLarge, R.drawable.large03);
        } else {
            ivImage.setImageResource(R.drawable.kentang);
            tvPrice.setText("Rp 12.000");
            
            setSmallIcon(rbSmall, R.drawable.small01);
            setSmallIcon(rbMedium, R.drawable.medium02);
            setSmallIcon(rbLarge, R.drawable.large03);
        }

        RadioGroup rgSize = findViewById(R.id.rg_size);
        RadioGroup rgSugar = findViewById(R.id.rg_sugar);
        CheckBox cbCream = findViewById(R.id.cb_cream);
        CheckBox cbTopping = findViewById(R.id.cb_topping);
        CheckBox cbNasi = findViewById(R.id.cb_nasi);
        EditText etNote = findViewById(R.id.et_custom_note);
        Button btnAddMore = findViewById(R.id.btn_add_more);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnAddMore.setOnClickListener(v -> {
            MainActivity.addToCart();
            Toast.makeText(this, "Pesanan ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnConfirm.setOnClickListener(v -> {
            int sizeId = rgSize.getCheckedRadioButtonId();
            if (sizeId == -1) {
                Toast.makeText(this, "Pilih ukuran terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }
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

            Intent intent = new Intent(DetailOrderActivity.this, SelectTableActivity.class);
            intent.putExtra("MENU", selectedMenu);
            intent.putExtra("SIZE", size);
            intent.putExtra("SUGAR", sugar);
            intent.putExtra("ADDONS", addons.toString());
            intent.putExtra("NOTE", note);
            startActivity(intent);
        });
    }

    private void setSmallIcon(RadioButton rb, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
        Drawable drawable = new BitmapDrawable(getResources(), resized);
        rb.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }
}