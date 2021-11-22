package com.example.mstore.ui.MyList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AccountFragment;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


public class EditProduct extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    protected static Product product;

    TextView productName;
    TextView productPrice;
    TextView productInfo;
    TextView productPassInEdit;
    ImageView productImage;
    String productImagePath;
    Button browseInEdit;
    Button submitUpdate;

    TextView productPassInRemove;
    Button submitRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productName = findViewById(R.id.productNameEdit);
        productPrice = findViewById(R.id.productPriceEdit);
        productInfo = findViewById(R.id.infoProductEdit);
        productPassInEdit = findViewById(R.id.userPassEdit);
        productImage = findViewById(R.id.imageViewProductEdit);
        browseInEdit = findViewById(R.id.browseProductEdit);
        submitUpdate = findViewById(R.id.updateSubmit);
        productPassInRemove = findViewById(R.id.userPassEditRemove);
        submitRemove = findViewById(R.id.removeSubmit);
        if (product != null) {
            productName.setText(product.name);
            productPrice.setText(Integer.toString(product.price));
            productInfo.setText(product.information);
            productImagePath = product.imagePath;
        }
        submitUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (productPassInEdit.getText().toString().equals(AccountFragment.currentUser.password)) {
                    Repository.getInstance(getApplicationContext()).updateProduct(productName.getText().toString(),
                            Integer.parseInt(productPrice.getText().toString())
                            , productImagePath, productInfo.getText().toString(), product.id, new RepositoryCallback<List<Void>>() {
                                @Override
                                public void onComplete(Result<List<Void>> result) {
                                    EditProduct.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (result instanceof Result.Success) {
                                                Intent intent = new Intent(EditProduct.this, HomeActivity.class);
                                                startActivity(intent);
                                            } else
                                                Toast.makeText(getApplicationContext(), "database Err", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                    );
                } else
                    Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_LONG).show();
            }
        });


        Bitmap bitmap;
        File imgFile = new File(productImagePath);
        if (imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = productImage;
            myImage.setImageBitmap(bitmap);
        }


        browseInEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        submitRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productPassInRemove.getText().toString().equals(AccountFragment.currentUser.password)) {
                    Repository.getInstance(getApplicationContext()).deleteProduct(product.id, new RepositoryCallback<List<Void>>() {
                        @Override
                        public void onComplete(Result<List<Void>> result) {
                            EditProduct.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result instanceof Result.Success) {
                                        Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
                                            @Override
                                            public void onComplete(Result<List<User>> result) {
                                                if (result instanceof Result.Success){
                                                    List<User> users = ((Result.Success<List<User>>) result).data;
                                                    for (User x:users){
                                                        if (x.id==AccountFragment.currentUser.id){
                                                            Repository.getInstance(getApplicationContext()).userUpdate(x.name, x.email, x.password, x.imagePath, x.phone, x.id, x.loginCount, x.productCount - 1, new RepositoryCallback<List<Void>>() {
                                                                @Override
                                                                public void onComplete(Result<List<Void>> result) {
                                                                    if (result instanceof Result.Error)
                                                                        Toast.makeText(getApplicationContext(), "increase productCount failed !", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                        Intent intent = new Intent(EditProduct.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(getApplicationContext(), "database Err", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });



                } else
                    Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

            String picturePath = cursor.getString(columnIndex);

            Bitmap bitmap;
            File imgFile = new File(picturePath);

            if (imgFile.exists()) {

                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    ImageView myImage = (ImageView) findViewById(R.id.imageViewProductEdit);
                    productImagePath = picturePath;
                    myImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


            cursor.close();

        }
    }


}