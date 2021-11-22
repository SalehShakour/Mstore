package com.example.mstore.ui.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mstore.ui.data.dao.ProductDao;
import com.example.mstore.ui.data.dao.UserDao;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;

@Database(entities = {User.class, Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProductDao productDao();
}
