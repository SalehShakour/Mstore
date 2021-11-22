package com.example.mstore.ui.Account.adminOption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AdminMenu;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.Store.CompleteProductInfo;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.io.File;
import java.sql.BatchUpdateException;
import java.util.List;


public class AllUserCompInfo extends AppCompatActivity {
    static User user;
    public ImageView imageView;
    public TextView userName;
    public TextView email;
    public TextView password;
    public TextView loginCount;
    public TextView phone;
    public Button back;
    public Button adminPage;
    public TextView admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_comp_info);
        imageView = findViewById(R.id.CMA_imageView);
        adminPage = findViewById(R.id.CMA_backHome);
        userName = findViewById(R.id.CMA_name);
        email = findViewById(R.id.CMA_email);
        password = findViewById(R.id.CMA_password);
        loginCount = findViewById(R.id.CMA_loginCount);
        phone = findViewById(R.id.CMA_phone);
        back = findViewById(R.id.CMA_adminPage);
        admin = findViewById(R.id.CMA_admin);
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
        loginCount.setText(Integer.toString(user.loginCount));
        phone.setText(user.phone);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllUserCompInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AllUserCompInfo.this, AdminMenu.class);
                        startActivity(intent);
                    }
                });

            }
        });
        adminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllUserCompInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AllUserCompInfo.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });


    }

}