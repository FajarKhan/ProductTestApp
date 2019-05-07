package com.example.prodecttestapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prodecttestapp.R;
import com.example.prodecttestapp.model.data;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> implements Filterable {

    Context mCtx;
    List<data> productList;
    List<data> productListFiltered;
    ProductItemClickListener itemClickListener;

        public ProductListAdapter(Context mCtx, List<data> productList, ProductItemClickListener itemClickListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.itemClickListener = itemClickListener;
        this.productListFiltered = productList;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.product_item_view, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        final data product = productList.get(position);

        Glide.with(mCtx)
                .load(product.getImage())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(holder.ivProduct);

        if (product.getName() != null)
            holder.tvName.setText(product.getName());
        if (product.getCategory_name() != null)
            holder.tvCategory.setText(product.getCategory_name());
        if (product.getQty() != null)
            holder.tvQty.setText("QTY : " + product.getQty());
        if (product.getPrice() != null)
            holder.tvAmount.setText("₹" + product.getPrice());

        //product on click
        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onitemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productList = productListFiltered;
                } else {
                    List<data> filteredList = new ArrayList<>();
                    for (data row : productListFiltered) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ProductItemClickListener {
        void onitemClick(data product);
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivProduct;
        AppCompatTextView tvName, tvCategory, tvQty, tvAmount;
        CardView cvProduct;

        public ProductListViewHolder(View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.iv_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvQty = itemView.findViewById(R.id.tv_qty);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            cvProduct = itemView.findViewById(R.id.cv_product);
        }
    }
}
