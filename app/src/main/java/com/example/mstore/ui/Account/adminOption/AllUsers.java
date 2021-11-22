package com.example.mstore.ui.Account.adminOption;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstore.R;
import com.example.mstore.ui.Store.StoreAdapter;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.util.ArrayList;
import java.util.List;


public class AllUsers extends AppCompatActivity {
    public List<User> users;
    public AllUserAdapter allUserAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_allUser);
        users = new ArrayList<>();
        Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
            @Override
            public void onComplete(Result<List<User>> result) {
                AllUsers.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success){
                            users = ((Result.Success<List<User>>) result).data;
                            allUserAdapter = new AllUserAdapter(users);
                            recyclerView.setAdapter(allUserAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                        }else Toast.makeText(getApplicationContext(),"database Err",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        allUserAdapter = new AllUserAdapter(users);
        recyclerView.setAdapter(allUserAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));




    }


}