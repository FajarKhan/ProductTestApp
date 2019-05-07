package com.example.prodecttestapp.Network;

import com.example.prodecttestapp.model.ProductModel;
import com.example.prodecttestapp.model.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface productApi {
    String BASE_URL = " http://52.25.13.115:7000/";

    @Headers({"Accept: application/json"})
    @GET("interview/productList")
    Call<ProductModel> getProducts();


    @GET("/interview/productDetail/{productId}")
    Call<data> getProductDetail(@Path(value = "productId", encoded = true) String productId);
}
