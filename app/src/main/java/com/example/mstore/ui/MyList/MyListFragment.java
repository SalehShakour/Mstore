package com.example.mstore.ui.MyList;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AccountFragment;

import com.example.mstore.ui.data.entities.Product;

import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyListFragment extends Fragment {

    List<Product> myProducts;
    MyListAdaptor myListAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerObject, Bundle savedInstanceState) {
        super.onCreateView(inflater, containerObject, savedInstanceState);

        View mainView = inflater.inflate(R.layout.fragment_mylist, containerObject, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.recycler_view_myList);
        myProducts = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) mainView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(getContext(), AddProduct.class);
                startActivity(switchActivityIntent);
            }
        });

        Repository.getInstance(requireActivity().getApplicationContext()).getAllProduct(new RepositoryCallback<List<Product>>() {
            @Override
            public void onComplete(Result<List<Product>> result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result instanceof Result.Success){
                            List<Product> products = ((Result.Success<List<Product>>)result).data;
                            for (Product x:products){
                                if (Integer.parseInt(x.userId) == AccountFragment.currentUser.id){
                                    myProducts.add(x);
                                }
                            }

                            myListAdaptor = new MyListAdaptor(myProducts);
                            recyclerView.setAdapter(myListAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));



                        }else Toast.makeText(requireContext().getApplicationContext(), "Err (loading database)", Toast.LENGTH_SHORT).show();



                    }
                });

            }
        });

        myListAdaptor = new MyListAdaptor(myProducts);
        recyclerView.setAdapter(myListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        return mainView;

    }


}



