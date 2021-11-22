package com.example.mstore.ui.Store;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstore.R;
import com.example.mstore.ui.MyList.EditProduct;
import com.example.mstore.ui.data.entities.Product;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Product> products;
    static Product product;

    public StoreAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StoreViewHolder holder, int position) {
        holder.bind(products.get(position));

    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        else return products.size();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice;
        ImageView productImage;
        View view;

        public StoreViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameStore);
            productPrice = itemView.findViewById(R.id.productPriceStore);
            productImage = itemView.findViewById(R.id.productImageViewStore);
            view = itemView;

        }
        public void bind(Product product){
            StoreAdapter.product = product;
            productName.setText(product.name);
            productPrice.setText(Integer.toString(product.price));
            try {
                File imgFile = new File(product.imagePath);
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                productImage.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(itemView.getContext(), "Permission denied",Toast.LENGTH_LONG).show();
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CompleteProductInfo.product=StoreAdapter.product;
                    v.getContext().startActivity(new Intent(v.getContext(), CompleteProductInfo.class));
                }
            });

        }
    }
}
