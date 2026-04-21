package com.example.reservationer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        TextView tvReceiptContent = findViewById(R.id.tv_receipt_content);
        Button btnBack = findViewById(R.id.btn_back_to_main);

        String data = readFromFile();
        tvReceiptContent.setText(data);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private String readFromFile() {
        String filename = "receipt.txt";
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = openFileInput(filename)) {
            int content;
            while ((content = fis.read()) != -1) {
                sb.append((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membaca struk", Toast.LENGTH_SHORT).show();
            return "Data tidak ditemukan.";
        }
        return sb.toString();
    }
}