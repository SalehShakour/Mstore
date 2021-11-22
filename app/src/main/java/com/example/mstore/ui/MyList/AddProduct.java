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
import com.example.mstore.ui.Login.LoginActivity;
import com.example.mstore.ui.Login.Register;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


public class AddProduct extends AppCompatActivity {

//    private static boolean checkPass = false;
    static String imgPath;
    static Uri selectedImageTemp;


    private static int RESULT_LOAD_IMAGE = 1;


    public ImageView productImage;
    public TextView productName;
    public TextView productPrice;
    public TextView info;

    public TextView userPassword;
    public Button browse;
    public Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productImage = findViewById(R.id.imageViewProduct);
        productName = findViewById(R.id.productNameAdd);
        productPrice = findViewById(R.id.productPriceAdd);
        info = findViewById(R.id.infoProductAdd);

        userPassword = findViewById(R.id.userPassAdd);
        browse = findViewById(R.id.browseProduct);
        submit = findViewById(R.id.AddSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imgPath != null){
                    if (userPassword.getText().toString().equals(AccountFragment.currentUser.password)) {

                        Repository.getInstance(getApplicationContext()).insertProduct(productName.getText().toString(),
                                AccountFragment.currentUser.id, Integer.parseInt(productPrice.getText().toString()), imgPath, info.getText().toString(), new RepositoryCallback<List<Void>>() {
                                    @Override
                                    public void onComplete(Result<List<Void>> result) {
                                        AddProduct.this.runOnUiThread(new Runnable() {
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
                                                                        Repository.getInstance(getApplicationContext()).userUpdate(x.name, x.email, x.password, x.imagePath, x.phone, x.id, x.loginCount, x.productCount + 1, new RepositoryCallback<List<Void>>() {
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

                                                    Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
                                                    imgPath = null;

                                                    Intent intent = new Intent(AddProduct.this, HomeActivity.class);
                                                    AddProduct.this.startActivity(intent);

                                                }
                                            }
                                        });
                                    }
                                });


                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_SHORT).show();

                    }
                }else Toast.makeText(getApplicationContext(), "Please Select An Image", Toast.LENGTH_SHORT).show();
            }


        });


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            selectedImageTemp = selectedImage;
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
                    ImageView myImage = (ImageView) findViewById(R.id.imageViewProduct);

                    myImage.setImageBitmap(bitmap);
                    imgPath = picturePath;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
            cursor.close();

        }
    }

//    public boolean checkUserPass(String password, int userId) {
//        Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
//            @Override
//            public void onComplete(Result<List<User>> result) {
//                AddProduct.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (result instanceof Result.Success) {
//                            List<User> users = ((Result.Success<List<User>>) result).data;
//                            for (User x : users) {
//                                if (userId == x.id) {
//                                    if (password.equals(x.password)) {
//                                        checkPass = true;
//                                    } else
//                                        Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_SHORT).show();
//                                } else
//                                    Toast.makeText(getApplicationContext(), "User Not Found !", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        return checkPass;
//    }
}