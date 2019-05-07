package com.example.prodecttestapp.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prodecttestapp.Network.productApi;
import com.example.prodecttestapp.model.ProductModel;
import com.example.prodecttestapp.model.data;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductViewModel extends AndroidViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<ProductModel> productList;
    private MutableLiveData<data> productDetail;

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    //we will call this method to get the data
    public LiveData<ProductModel> getProducts() {
        if (productList == null) {
            productList = new MutableLiveData<ProductModel>();
            loadProduct();
        }
        return productList;
    }

    public LiveData<data> getProductDetail(String productID) {
        if (productDetail == null) {
            productDetail = new MutableLiveData<data>();
            loadProductDetails(productID);
        }
        return productDetail;
    }

    private void loadProductDetails(String productID) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(productApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productApi api = retrofit.create(productApi.class);
        Call<data> call = api.getProductDetail(productID);

        call.enqueue(new Callback<data>() {
            @Override
            public void onResponse(Call<data> call, Response<data> response) {
                if (response.isSuccessful()) {
                    productDetail.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<data> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getApplication(), "30 seconds connection timed out!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadProduct() {
        //setting 30 second timeout
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(productApi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productApi api = retrofit.create(productApi.class);
        Call<ProductModel> call = api.getProducts();

        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {

                if (response.isSuccessful()) {
                    productList.setValue(response.body());
                } else {
                    Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(getApplication(), "30 seconds connection timed out!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}