package com.example.mstore.ui.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    public User(String name, String email, String password, String imagePath,String phone,boolean admin,int loginCount,int productCount) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
        this.phone = phone;
        this.admin = admin;
        this.loginCount = loginCount;
        this.productCount = productCount;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "loginCount")
    public int loginCount;

    @ColumnInfo(name = "productCount")
    public int productCount;

    @ColumnInfo(name = "admin")
    public boolean admin;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "phone")
    public String phone;
}
