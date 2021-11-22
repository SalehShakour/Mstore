package com.example.mstore.ui.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("INSERT INTO Product(name,userId,price,image_path,information) VALUES(:name,:userId,:price,:imagePath,:info)")
    void insert(String name,int userId,int price,String imagePath,String info);

    @Query("SELECT * FROM Product")
    List<Product> getAll();

    @Query("SELECT * FROM Product WHERE userId = :productUserId ")
    List<Product> loadProductsByUserId(int productUserId);

    @Insert
    void insertAll(Product... products);

    @Query("DELETE FROM Product WHERE id=:productId;")
    void delete(int productId);

    @Query("UPDATE Product SET name =:name,price =:newPrice ,information=:information,image_path=:imagePath WHERE id =(:productId)")
    void updateProduct(String name, int newPrice, String imagePath, String information, int productId);
}
