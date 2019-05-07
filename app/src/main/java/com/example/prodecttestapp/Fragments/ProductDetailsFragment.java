package com.example.prodecttestapp.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prodecttestapp.MainActivity;
import com.example.prodecttestapp.R;
import com.example.prodecttestapp.Utils.Constants;
import com.example.prodecttestapp.ViewModel.ProductViewModel;
import com.example.prodecttestapp.model.data;

public class ProductDetailsFragment extends Fragment {

    private AppCompatTextView tvName, tvCategory, tvQty, tvAmount, tvSize;
    private AppCompatImageView ivProduct;
    private View view;
    private String productID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_details, container, false);

        initlizeResorces();
        getData();
        setData();

        return view;
    }

    private void getData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            productID = bundle.getString(Constants.PRODUCT_ID, "");
        }
    }

    private void initlizeResorces() {
        tvName = view.findViewById(R.id.tv_name);
        tvCategory = view.findViewById(R.id.tv_category);
        ivProduct = view.findViewById(R.id.iv_image);
        tvAmount = view.findViewById(R.id.tv_amount);
        tvSize = view.findViewById(R.id.tv_size);

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Product Details");
    }

    private void setData() {
        ProductViewModel model = ViewModelProviders.of(this).get(ProductViewModel.class);

        model.getProductDetail(productID).observe(this, new Observer<data>() {
            @Override
            public void onChanged(@Nullable data productDetails) {
                if (productDetails != null) {

                    Glide.with(getActivity())
                            .load(productDetails.getImage())
                            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                            .into(ivProduct);

                    if (productDetails.getName() != null)
                        tvName.setText(productDetails.getName());
                    if (productDetails.getCategory_name() != null)
                        tvCategory.setText(productDetails.getCategory_name());
                    if (productDetails.getSize() != null)
                        tvSize.setText(productDetails.getSize());
                    if (productDetails.getPrice() != null)
                        tvAmount.setText("₹" + productDetails.getPrice());
                }
            }
        });
    }
}
