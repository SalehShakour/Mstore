package com.example.mstore.ui.Store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstore.R;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StoreFragment extends Fragment {
    List<Product> products;
    public StoreAdapter storeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerObject, Bundle savedInstanceState) {
        super.onCreateView(inflater, containerObject, savedInstanceState);
        View mainView = inflater.inflate(R.layout.fragment_store, containerObject, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.recycler_view);
        products = new ArrayList<>();


        Repository.getInstance(requireContext().getApplicationContext()).getAllProduct(new RepositoryCallback<List<Product>>() {
            @Override
            public void onComplete(Result<List<Product>> result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success) {
                            products = ((Result.Success<List<Product>>) result).data;
                            storeAdapter = new StoreAdapter(products);
                            recyclerView.setAdapter(storeAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

                        } else
                            Toast.makeText(requireContext().getApplicationContext(), "Err (loading database)", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        storeAdapter = new StoreAdapter(products);
        recyclerView.setAdapter(storeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
//


        return mainView;
    }
}