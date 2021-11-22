package com.example.mstore.ui.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Account.adminOption.AllUsers;
import com.example.mstore.ui.Account.adminOption.TopSeller;
import com.example.mstore.ui.Account.adminOption.TotalPrice;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.Login.LoginActivity;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class AdminMenu extends AppCompatActivity {
    View allUser;
    View topSeller;
    View loginControl;
    Button back;

    public TreeMap<Integer,User> userTreeMap = new TreeMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        allUser = findViewById(R.id.A_allUser);
        topSeller = findViewById(R.id.A_topSeller);
        loginControl = findViewById(R.id.A_loginControl);
        back = findViewById(R.id.A_back);

        allUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMenu.this, AllUsers.class));
            }
        });

        topSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
                    @Override
                    public void onComplete(Result<List<User>> result) {
                        AdminMenu.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result instanceof Result.Success){
                                    List<User> users = ((Result.Success<List<User>>) result).data;
                                    for (User x:users){
                                        userTreeMap.put(x.productCount,x);
                                    }
                                    TopSeller.user = userTreeMap.get(userTreeMap.lastKey());
                                    startActivity(new Intent(AdminMenu.this, TopSeller.class));
                                }
                            }
                        });
                    }
                });


            }
        });

        loginControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance(getApplicationContext()).getAllProduct(new RepositoryCallback<List<Product>>() {
                    @Override
                    public void onComplete(Result<List<Product>> result) {
                        AdminMenu.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result instanceof Result.Success){
                                    List<Product> products = ((Result.Success<List<Product>>) result).data;
                                    TotalPrice.nProduct = products.size();
                                    int tPrice = 0;
                                    for (Product x:products){
                                        tPrice += x.price;
                                    }
                                    TotalPrice.tPrice=tPrice;
                                    startActivity(new Intent(AdminMenu.this, TotalPrice.class));

                                }
                            }
                        });
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminMenu.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AdminMenu.this, HomeActivity.class));
                    }
                });
            }
        });




    }


}