package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTableNumber;
    private String tableNo = "";
    private RecyclerView rvMenuHome;
    private TextView tvCartCount;
    private CardView cvTableMap;
    private static int cartItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTableNumber = findViewById(R.id.tv_table_number);
        rvMenuHome = findViewById(R.id.rv_menu_home);
        tvCartCount = findViewById(R.id.tv_cart_count);
        cvTableMap = findViewById(R.id.cv_table_map);
        FloatingActionButton btnScan = findViewById(R.id.btn_scan);

        updateCartUI();
        setupMenu();

        btnScan.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt(getString(R.string.scan_prompt));
            integrator.setOrientationLocked(true);
            integrator.setBeepEnabled(true);
            integrator.setCaptureActivity(CaptureActivityPortrait.class);
            integrator.initiateScan();
        });

        findViewById(R.id.btn_cart).setOnClickListener(v -> {
            if (cartItemCount > 0) {
                Toast.makeText(this, "Membuka Keranjang...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Keranjang Anda kosong", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_notification).setOnClickListener(v -> {
            Toast.makeText(this, "Tidak ada notifikasi baru", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.nav_history).setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.history_coming_soon), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartUI();
        if (!tableNo.isEmpty()) {
            cvTableMap.setVisibility(View.VISIBLE);
        }
    }

    private void updateCartUI() {
        if (cartItemCount > 0) {
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(cartItemCount));
        } else {
            tvCartCount.setVisibility(View.GONE);
        }
    }

    public static void addToCart() {
        cartItemCount++;
    }

    private void setupMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Kopi Susu", "Kopi robusta dengan susu kental manis", "Rp 15.000", R.drawable.kopsu));
        menuItems.add(new MenuItem("Nasi Goreng", "Nasi goreng spesial dengan telur", "Rp 25.000", R.drawable.nasgor));
        menuItems.add(new MenuItem("Kentang Goreng", "Camilan gurih dan renyah", "Rp 12.000", R.drawable.kentang));

        rvMenuHome.setLayoutManager(new LinearLayoutManager(this));
        rvMenuHome.setAdapter(new MenuAdapter(menuItems));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, R.string.scan_cancelled, Toast.LENGTH_LONG).show();
            } else {
                tableNo = result.getContents();
                tvTableNumber.setText(getString(R.string.table_number_format, tableNo));
                cvTableMap.setVisibility(View.VISIBLE);
                Toast.makeText(this, getString(R.string.table_detected, tableNo), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    static class MenuItem {
        String name, desc, price;
        int imageResId;
        MenuItem(String name, String desc, String price, int imageResId) {
            this.name = name;
            this.desc = desc;
            this.price = price;
            this.imageResId = imageResId;
        }
    }

    class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
        List<MenuItem> items;
        MenuAdapter(List<MenuItem> items) { this.items = items; }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_home, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MenuItem item = items.get(position);
            holder.tvName.setText(item.name);
            holder.tvDesc.setText(item.desc);
            holder.tvPrice.setText(item.price);
            holder.ivImage.setImageResource(item.imageResId);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, DetailOrderActivity.class);
                intent.putExtra("SELECTED_MENU", item.name);
                intent.putExtra("TABLE_NUMBER", tableNo);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() { return items.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvDesc, tvPrice;
            ImageView ivImage;
            ViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tv_menu_name);
                tvDesc = v.findViewById(R.id.tv_menu_desc);
                tvPrice = v.findViewById(R.id.tv_menu_price);
                ivImage = v.findViewById(R.id.iv_menu_image);
            }
        }
    }
}