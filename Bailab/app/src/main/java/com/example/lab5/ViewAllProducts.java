package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllProducts extends AppCompatActivity implements Adapter_product.Callback{
    ListView lvProduct;
    List<Model_product> mList;
    Adapter_product adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_products);
        lvProduct = findViewById(R.id.listProducts);
        mList = new ArrayList<>();
        callApi();


    }

    private void loadList(List list){
        if (list != null) {
            adapter = new Adapter_product((ArrayList<Model_product>) list,this,this);
            lvProduct.setAdapter(adapter);
        }
    }
    private void callApi(){
        Api_service.apiService.getProduct().enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response) {
                if (response.isSuccessful()){
                    List<Model_product> list =response.body();
                    loadList(list);
                }else{
                    Toast.makeText(ViewAllProducts.this,"Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t) {
                Toast.makeText(ViewAllProducts.this, "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void detail(Model_product model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        builder.setView(dialogView);

        EditText edName = dialogView.findViewById(R.id.edtProductName);
        EditText edPrice= dialogView.findViewById(R.id.edtProductPrice);
        EditText edDes= dialogView.findViewById(R.id.edtProductDes);
        TextView id = dialogView.findViewById(R.id.tvId);
        Button btnSave= dialogView.findViewById(R.id.btnSave);
        Button btnDel= dialogView.findViewById(R.id.btnDel);

        edName.setText(model.getName());
        edPrice.setText(model.getPrice());
        edDes.setText(model.getDescription());
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct(model.getId(), edName.getText().toString(),edPrice.getText().toString(),edDes.getText().toString());
                dialog.dismiss();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(model.getId());
                dialog.dismiss();
            }
        });


    }
    private void updateProduct(String id, String name, String price, String des){
        Model_product productModel = new Model_product();
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setDescription(des);

        Call<List<Model_product>> call = Api_service.apiService.updateProduct(id,productModel);
        call.enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ViewAllProducts.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    List<Model_product> tableItems = response.body();
                    loadList(tableItems);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }
    private void deleteProduct(String id) {
        Call<List<Model_product>> call = Api_service.apiService.deleteProduct(id);
        call.enqueue(new Callback<List<Model_product>>() {
            @Override
            public void onResponse(Call<List<Model_product>> call, Response<List<Model_product>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ViewAllProducts.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    List<Model_product> tableItems = response.body();
                    loadList(tableItems);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Model_product>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }
}