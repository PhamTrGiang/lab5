package com.example.lab5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api_service {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    Api_service apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api_service.class);

    @GET("listProduct")
    Call<List<Model_product>> getProduct();


    @POST("addProduct")
    Call<List<Model_product>> addProduct(@Body Model_product model_product);

    @PUT("/product/{id}")
    Call<List<Model_product>> updateProduct(@Path("id") String id, @Body Model_product model_product);

    @DELETE("/product/{id}")
    Call<List<Model_product>> deleteProduct(@Path("id") String id);
}
