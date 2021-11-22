package com.example.mstore.ui.Store;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.io.File;
import java.util.List;


public class CompleteProductInfo extends AppCompatActivity {
    static Product product;

    ImageView productImg;
    TextView productName;
    TextView productInfo;
    TextView productPrice;
    TextView productUserPhone;
    Button back;
    Button call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_product_info);

        productName = findViewById(R.id.CMP_name);
        productInfo = findViewById(R.id.CMP_info);
        productPrice = findViewById(R.id.CMP_price);
        productUserPhone = findViewById(R.id.CMP_userNumber);
        back = findViewById(R.id.backToStore);
        productImg = findViewById(R.id.CMP_imageView);

        productName.setText(product.name);
        productInfo.setText(product.information);
        productPrice.setText(Integer.toString(product.price));
        call = findViewById(R.id.call);

        Bitmap bitmap;
        File imgFile = new File(product.imagePath);
        if (product.imagePath!=null && imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            productImg.setImageBitmap(bitmap);
        }

        Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
            @Override
            public void onComplete(Result<List<User>> result) {
                CompleteProductInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success){
                            List<User> users = ((Result.Success<List<User>>) result).data;
                            for (User x:users){
                                if (x.id==Integer.parseInt(product.userId))
                                    productUserPhone.setText(x.phone);
                            }
                        }else Toast.makeText(getApplicationContext(),"database Err",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteProductInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(CompleteProductInfo.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("phone number",productUserPhone.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied To Clipboard !",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel",productUserPhone.getText().toString(), null));
                startActivity(intent);

                CompleteProductInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });

    }

}