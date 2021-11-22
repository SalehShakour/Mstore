package com.example.mstore.ui.Login;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AccountFragment;
import com.example.mstore.ui.data.entities.Product;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mstore.databinding.ActivityRegisterBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    private static boolean admin = false;
    public static String imgPath;
    public static Uri selectedImageTemp;
    private static boolean isChecked;


    private static int RESULT_LOAD_IMAGE = 1;


    private AppBarConfiguration appBarConfiguration;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        Button browse = findViewById(R.id.browse);
        Button registerButton = findViewById(R.id.register_button);
        TextView userName = findViewById(R.id.Register_username);
        TextView email = findViewById(R.id.Register_email);
        TextView password = findViewById(R.id.Register_password);
        TextView repeatPassword = findViewById(R.id.repeatPass);
        TextView phone = findViewById(R.id.register_Number);
        TextView passPhrase = findViewById(R.id.PassPhrase);
        Switch adminSwitch = findViewById(R.id.adminSwitch);
        passPhrase.setVisibility(View.INVISIBLE);

        adminSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Register.isChecked = isChecked;
                if (isChecked) {
                    passPhrase.setVisibility(View.VISIBLE);
                } else passPhrase.setVisibility(View.INVISIBLE);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(repeatPassword.getText().toString())) {
                    if (!userName.getText().toString().equals("") && !email.getText().toString().equals("")) {
                        if (!isChecked || passPhrase.getText().toString().equals("adminadmin")) {
                            Repository.getInstance(getApplicationContext()).insertUser(
                                    userName.getText().toString(), email.getText().toString(), password.getText().toString(), imgPath, phone.getText().toString(), isChecked
                                    ,0,0, new RepositoryCallback<List<User>>() {
                                        @Override
                                        public void onComplete(Result<List<User>> result) {
                                            Register.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Register.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (result instanceof Result.Success) {
                                                                Toast.makeText(getApplicationContext(), "Done! Please Login. ", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(Register.this, LoginActivity.class);
                                                                Register.this.startActivity(intent);
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Err", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    });
                        }else Toast.makeText(getApplicationContext(), "Wrong Security Phrase !", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
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
                    ImageView myImage = (ImageView) findViewById(R.id.imageView);

                    myImage.setImageBitmap(bitmap);
                    imgPath = picturePath;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


            cursor.close();

        }
    }
}