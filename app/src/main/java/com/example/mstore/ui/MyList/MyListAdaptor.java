package com.example.mstore.ui.MyList;

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
import com.example.mstore.ui.Store.StoreAdapter;
import com.example.mstore.ui.data.entities.Product;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class MyListAdaptor extends RecyclerView.Adapter<MyListAdaptor.MyListViewHolder> {

    private List<Product> products;

    public MyListAdaptor(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mylist, parent, false);
        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyListViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        else return products.size();
    }

    static class MyListViewHolder extends RecyclerView.ViewHolder{

        TextView productName,productPrice;
        ImageView productImage;
        static Product product;
        View view;

        public MyListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);


            productName = itemView.findViewById(R.id.productNameMyList);
            productPrice = itemView.findViewById(R.id.productPriceMyList);
            productImage = itemView.findViewById(R.id.productImageViewMyList);
            view = itemView;
        }
        public void bind(Product product){
            MyListViewHolder.product = product;
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
                    EditProduct.product = MyListViewHolder.product;
                    v.getContext().startActivity(new Intent(v.getContext(), EditProduct.class));
                }
            });

        }
    }
}
