package com.example.mstore.ui.Login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AccountFragment;
import com.example.mstore.ui.data.entities.User;
import com.example.mstore.ui.data.repository.Repository;
import com.example.mstore.ui.data.repository.RepositoryCallback;
import com.example.mstore.ui.data.repository.Result;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static String personEmail;
    public static String personUsername;

    private GoogleSignInClient mGoogleSignInClient;
    TextView forgetPass;
    Button okButton;
    SignInButton signInButton;
    ActivityResultLauncher<Intent> mStartForResult;
    TextView registerText;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        TextView userName = findViewById(R.id.loginUsername);
        TextView email = findViewById(R.id.loginEmail);
        TextView password = findViewById(R.id.logonPassword);

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        forgetPass = findViewById(R.id.forget_password);
        okButton = findViewById(R.id.ok);
        signInButton = findViewById(R.id.signIn);
        registerText = findViewById(R.id.registerText);


        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                            handleSignInResult(task);
                            // Handle the Intent
                        }
                    }


                });


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(LoginActivity.this, Register.class);
                startActivity(switchActivityIntent);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance(getApplicationContext()).getAllUser(new RepositoryCallback<List<User>>() {
                    @Override
                    public void onComplete(Result<List<User>> result) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result instanceof Result.Success) {
                                    List<User> users = ((Result.Success<List<User>>)result).data;
                                    for (User x:users){
                                        if (userName.getText().toString().equals(x.name) && email.getText().toString().equals(x.email))
                                            if (password.getText().toString().equals(x.password)){
                                                AccountFragment.currentUser = x;
                                                AccountFragment.imagePath=x.imagePath;
                                                personUsername = x.name;
                                                personEmail = x.email;


                                                Repository.getInstance(getApplicationContext()).userUpdate(x.name, x.email, x.password, x.imagePath, x.phone, x.id, x.loginCount + 1,x.productCount, new RepositoryCallback<List<Void>>() {
                                                    @Override
                                                    public void onComplete(Result<List<Void>> result) {
                                                        if (result instanceof Result.Error)
                                                            Toast.makeText(getApplicationContext(), "Login Count Failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                Intent switchActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(switchActivityIntent);




                                            }else Toast.makeText(getApplicationContext(), "Wrong Password !", Toast.LENGTH_SHORT).show();
                                    }if (personEmail==null && personUsername == null) Toast.makeText(getApplicationContext(), "User Not Found !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });


            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Not Ready!", Toast.LENGTH_LONG).show();

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Google SignIn not Ready!",Toast.LENGTH_LONG).show();
//                signIn();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mStartForResult.launch(signInIntent);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount acct) {
        if (acct != null) {
            personUsername = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }else {
            Toast.makeText(this,"Could not sign with google",Toast.LENGTH_SHORT).show();
        }
    }
    
}
