package com.example.mstore.ui.Account.adminOption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AdminMenu;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.data.entities.User;

import java.io.File;


public class TopSeller extends AppCompatActivity {
    public static User user;
    public ImageView imageView;
    public TextView userName;
    public TextView email;
    public TextView password;
    public TextView productCount;
    public TextView phone;
    public Button back;
    public Button home;
    public TextView admin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_seller);

        imageView = findViewById(R.id.TP_imageView);
        home = findViewById(R.id.TP_backHome);
        userName = findViewById(R.id.TP_name);
        email = findViewById(R.id.TP_email);
        password = findViewById(R.id.TP_password);
        productCount = findViewById(R.id.TP_productCount);
        phone = findViewById(R.id.TP_phone);
        back = findViewById(R.id.TP_back);
        admin = findViewById(R.id.TP_admin);
        if (!user.admin)
            admin.setVisibility(View.INVISIBLE);

        Bitmap bitmap;
        File imgFile = new File(user.imagePath);
        if (user.imagePath!=null && imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }


        userName.setText(user.name);
        email.setText(user.email);
        password.setText(user.password);
        productCount.setText(Integer.toString(user.productCount));
        phone.setText(user.phone);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopSeller.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(TopSeller.this, AdminMenu.class);
                        startActivity(intent);
                    }
                });

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopSeller.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(TopSeller.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });


    }


}