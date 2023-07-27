package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProduct extends AppCompatActivity {
    EditText edName,edPrice,edDes;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        edName = findViewById(R.id.edtProductName);
        edPrice = findViewById(R.id.edtProductPrice);
        edDes = findViewById(R.id.edtProductDes);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct(edName.getText().toString(),edPrice.getText().toString(),edDes.getText().toString());
            }
        });
    }

    public void addProduct(String name,String price,String des){
        Model_product obj = new Model_product();
        obj.setName(name);
        obj.setPrice(price);
        obj.setDescription(des);
        Api_service.apiService.addProduct(obj).enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(AddProduct.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Xử lý lỗi khi thêm dữ liệu
                    Toast.makeText(AddProduct.this,"lỗi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t) {
                Toast.makeText(AddProduct.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }

}