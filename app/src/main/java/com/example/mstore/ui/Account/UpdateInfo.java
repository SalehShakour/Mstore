package com.example.mstore.ui.Account;

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
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.Login.LoginActivity;
import com.example.mstore.ui.MyList.EditProduct;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


public class UpdateInfo extends AppCompatActivity {
    private User user;

    private static int RESULT_LOAD_IMAGE = 1;

    public Button browse;
    public Button update;
    public TextView phone;
    public TextView email;
    public TextView userName;
    public TextView userPassword;
    public String imgPath;
    public ImageView userImage;
    public TextView oldPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        user = AccountFragment.currentUser;
        browse = findViewById(R.id.UPP_browse);
        update = findViewById(R.id.UPP_update);
        email = findViewById(R.id.UPP_emailEdit);
        userImage = findViewById(R.id.UPP_imageViewEdit);
        userName = findViewById(R.id.UPP_userNameEdit);
        userPassword = findViewById(R.id.UPP_userPassEdit);
        phone = findViewById(R.id.UPP_phoneNumber_Edit);
        oldPass = findViewById(R.id.oldPassword);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        if (user != null) {
            email.setText(user.email);
            userName.setText(user.name);
            phone.setText(user.phone);
            imgPath = user.imagePath;
            Bitmap bitmap;
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                userImage = (ImageView) findViewById(R.id.UPP_imageViewEdit);
                userImage.setImageBitmap(bitmap);
            }
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPass.getText().toString().equals(user.password)){
                    Repository.getInstance(getApplicationContext()).userUpdate(userName.getText().toString(),
                            email.getText().toString(), userPassword.getText().toString(),
                            imgPath,phone.getText().toString(), user.id,user.loginCount,user.productCount, new RepositoryCallback<List<Void>>() {
                                @Override
                                public void onComplete(Result<List<Void>> result) {
                                    UpdateInfo.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (result instanceof Result.Success) {
                                                AccountFragment.currentUser = null;
                                                LoginActivity.personEmail = null;
                                                LoginActivity.personUsername = null;
                                                Intent intent = new Intent(UpdateInfo.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else
                                                Toast.makeText(getApplicationContext(), "database Err", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                }else Toast.makeText(getApplicationContext(),"Wrong Password !", Toast.LENGTH_LONG).show();
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
                    userImage = (ImageView) findViewById(R.id.UPP_imageViewEdit);
                    imgPath = picturePath;
                    userImage.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();

        }
    }


}