package com.example.mstore.ui.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product")
public class Product {

    public Product(String name, String userId, int price, String imagePath, String information) {
        this.name = name;
        this.userId = userId;
        this.price = price;
        this.imagePath = imagePath;
        this.information = information;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "information")
    public String information;
}
