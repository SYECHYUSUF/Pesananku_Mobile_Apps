package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuListActivity extends AppCompatActivity {

    private String[] menuData = {"Kopi Susu", "Kentang Goreng", "Nasi Goreng", "Teh Manis", "Ayam Bakar"};
    private String tableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        tableNo = getIntent().getStringExtra("TABLE_NUMBER");

        ListView lvMenu = findViewById(R.id.lv_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuData);
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMenu = menuData[position];
            Intent intent = new Intent(MenuListActivity.this, DetailOrderActivity.class);
            intent.putExtra("SELECTED_MENU", selectedMenu);
            intent.putExtra("TABLE_NUMBER", tableNo);
            startActivity(intent);
        });
    }
}