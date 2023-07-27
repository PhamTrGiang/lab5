package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void viewProducts(View view){
        Intent i = new Intent(getApplicationContext(), ViewAllProducts.class);
        startActivity(i);
    }
    public void addProduct(View view){
        Intent i = new Intent(getApplicationContext(), AddProduct.class);
        startActivity(i);
    }

}