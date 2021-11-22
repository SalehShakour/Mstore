package com.example.mstore.ui.data.repository;

import android.content.Context;

import com.example.mstore.ui.MyList.MyListFragment;
import com.example.mstore.ui.application.MyApplication;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;

import java.util.List;

public class Repository {

    private static Repository repository;
    public LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    private Repository(Context context) {
        localDataSource = new LocalDataSource(context);
    }

    public static Repository getInstance(Context context) {
        if (repository == null)
            repository = new Repository(context);
        return repository;
    }

    //------------------------------Product-------------------------------------------

    public void insertProduct(String name, int userId, int price, String imagePath, String info,RepositoryCallback<List<Void>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.insertProduct(name, userId, price, imagePath, info);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });

    }

    public void getAllProduct(RepositoryCallback<List<Product>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> products = localDataSource.getAllProducts();
                    callback.onComplete(new Result.Success<>(products));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });

    }

    public void loadProductsByUserId(int productUserId,RepositoryCallback<List<Product>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Product> products =localDataSource.loadProductsByUserId(productUserId);
                    callback.onComplete(new Result.Success<>(products));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });

    }

    public void deleteProduct(int productId,RepositoryCallback<List<Void>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.deleteProduct(productId);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });

    }

    public void updateProduct(String name, int newPrice, String imagePath, String information, int productId,RepositoryCallback<List<Void>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.updateProduct(name,newPrice,imagePath,information,productId);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });

    }

    //------------------------------User-------------------------------------------
    public void insertUser(String name, String email, String password, String imagePath,String phone,boolean admin,int loginCount,int productCount, RepositoryCallback<List<User>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.insertUser(name, email, password, imagePath,phone,admin,loginCount,productCount);
                    callback.onComplete(new Result.Success<>(null));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }

    public void userUpdate(String name, String email, String password, String imagePath,String phone,int userId,int loginCount,int productCount,RepositoryCallback<List<Void>> callback){
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.updateUser(name, email, password, imagePath, phone, userId,loginCount,productCount);
                    callback.onComplete(new Result.Success<>(null));
                }catch (Exception e){
                    callback.onComplete(new Result.Error<>(e));
                }
            }
        });
    }


    public void getAllUser(RepositoryCallback<List<User>> callback) {
        MyApplication.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<User> users = localDataSource.allUser();
                    callback.onComplete(new Result.Success<>(users));
                } catch (Exception e) {
                    callback.onComplete(new Result.Error<>(e));
                }

            }
        });
    }

    public void deleteUser(int userId) {
        localDataSource.deleteUser(userId);
    }

    void updatePassword(String newPass, String id) {
        localDataSource.updatePassword(newPass, id);
    }


}
