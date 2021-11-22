package com.example.mstore.ui.Account;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mstore.R;
import com.example.mstore.ui.Login.HomeActivity;
import com.example.mstore.ui.Login.LoginActivity;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;


public class AccountFragment extends Fragment {
    public static User currentUser;

    public static String imagePath;
    public Button updateInfo;
    public Button refresh;

    ImageView myImage;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup containerObject, Bundle savedInstanceState) {
        super.onCreateView(inflater, containerObject, savedInstanceState);


        View mainView = inflater.inflate(R.layout.fragment_account, containerObject, false);
        myImage = (ImageView) mainView.findViewById(R.id.imageViewAcc);
        Switch profilePhoto = mainView.findViewById(R.id.switchPhoto);
        Button signOut = mainView.findViewById(R.id.SignOut);
        updateInfo = mainView.findViewById(R.id.updateInfoAcc);
        TextView accUsername = mainView.findViewById(R.id.UserNameAcc);
        TextView accEmail = mainView.findViewById(R.id.EmailAcc);
        Button monitor = mainView.findViewById(R.id.monitor);

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AdminMenu.class);
                startActivity(intent);
            }
        });
        View adminView = mainView.findViewById(R.id.adminLayout);
        if (!currentUser.admin)
            adminView.setVisibility(View.INVISIBLE);



        accUsername.setText(currentUser.name);
        accEmail.setText(currentUser.email);
        File imgFile = new File(currentUser.imagePath);
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        myImage.setImageBitmap(bitmap);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = null;
                LoginActivity.personEmail = null;
                LoginActivity.personUsername = null;
                Intent intent = new Intent(AccountFragment.this.getContext(), LoginActivity.class);
                AccountFragment.this.startActivity(intent);
            }
        });


        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateInfo.class);
                startActivity(intent);
            }
        });


        ImageView myImage = (ImageView) mainView.findViewById(R.id.imageViewAcc);
        profilePhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && imagePath != null) {
                    myImage.setVisibility(View.VISIBLE);
                } else myImage.setVisibility(View.INVISIBLE);
            }
        });
        return mainView;


    }
}