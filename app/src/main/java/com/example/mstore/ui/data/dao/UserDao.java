package com.example.mstore.ui.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mstore.ui.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("INSERT INTO User(name,email,password,image_path,phone,admin,loginCount,productCount) VALUES(:name,:email,:password,:imagePath,:phone,:admin,:loginCount,:productCount)")
    void insert(String name,String email,String password,String imagePath,String phone,boolean admin,int loginCount,int productCount);

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM User WHERE id=:userId;")
    void delete(int userId);

    @Query("UPDATE User SET password =(:newPass) WHERE id =(:id)")
    void updatePassword(String newPass,String id);

    @Query("UPDATE User SET name =:name,email=:email,password=:password,image_path=:imagePath,phone=:phone,loginCount=:loginCount,productCount=:productCount WHERE id =(:userId)")
    void updateUser(String name, String email, String password, String imagePath,String phone,int userId,int loginCount,int productCount);




}
