package com.example.mstore.ui.data.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.mstore.ui.data.AppDatabase;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;

import java.util.List;

public class LocalDataSource {
    private AppDatabase db;

    public LocalDataSource(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "M_store_database").build();
    }

    //------------------------------Product-------------------------------------------

    public List<Product> getAllProducts() {
        return db.productDao().getAll();
    }

    public void insertProduct(String name, int userId, int price, String imagePath, String info) {
        db.productDao().insert(name, userId, price, imagePath, info);
    }

    public List<Product> loadProductsByUserId(int productUserId) {
        return db.productDao().loadProductsByUserId(productUserId);
    }

    public void deleteProduct(int productId) {
        db.productDao().delete(productId);
    }

    public void updateProduct(String name, int newPrice, String imagePath, String information, int productId) {
        db.productDao().updateProduct(name,newPrice,imagePath,information,productId);
    }

    //------------------------------User-------------------------------------------
    public void insertUser(String name, String email, String password, String imagePath, String phone, boolean admin,int loginCount,int productCount) {
        db.userDao().insert(name, email, password, imagePath,phone,admin,loginCount,productCount);
    }
    public void updateUser(String name, String email, String password, String imagePath,String phone,int userId,int loginCount,int productCount){
        db.userDao().updateUser(name, email, password, imagePath, phone, userId,loginCount,productCount);
    }




    //todo optional User activity
    public List<User> allUser() {
        return db.userDao().getAll();
    }

    public void deleteUser(int userId) {
        db.userDao().delete(userId);
    }

    void updatePassword(String newPass, String id) {
        db.userDao().updatePassword(newPass, id);
            }


    //----------------------------




}
