package com.example.lab5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class Adapter_product extends BaseAdapter {

    ArrayList<Model_product> listProducts;
    Context context;
    private Callback callback;

    public Adapter_product(ArrayList<Model_product> listProducts,Context context, Callback callback) {
        this.context = context;
        this.listProducts = listProducts;
        this.callback = callback;
    }
    public void setTableItems(List<Model_product> list) {
        this.listProducts = (ArrayList<Model_product>) list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return listProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View classView;
        if (view == null){
            classView =View.inflate(viewGroup.getContext(), R.layout.list_item,null);
        }else{
            classView = view;
        }

        final Model_product objProduct = listProducts.get(i);

        TextView name = classView.findViewById(R.id.tvName);
        TextView price = classView.findViewById(R.id.tvPrice);
        LinearLayout ln = classView.findViewById(R.id.linear);

        name.setText(objProduct.getName());
        price.setText(objProduct.getPrice());

        ln.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callback.detail(objProduct);
                return false;
            }
        });

        return classView;
    }

    public  interface Callback{
        void detail(Model_product model);
    }

}
