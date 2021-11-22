package com.example.mstore.ui.Account.adminOption;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mstore.R;
import com.example.mstore.ui.Account.AdminMenu;

import org.w3c.dom.Text;


public class TotalPrice extends AppCompatActivity {
    public static int nProduct;
    public static int tPrice;
    public static float pAve;

    public TextView numberOfProduct;
    public TextView totalPrice;
    public TextView ave;
    public Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_price);

        numberOfProduct = findViewById(R.id.TT_numberProduct);
        totalPrice = findViewById(R.id.TT_totalPrice);
        ave = findViewById(R.id.TT_ave);
        back = findViewById(R.id.TT_back);

        totalPrice.setText(Integer.toString(tPrice));
        numberOfProduct.setText(Integer.toString(nProduct));
        ave.setText(Float.toString((float) tPrice/nProduct));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalPrice.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(TotalPrice.this, AdminMenu.class));
                    }
                });
            }
        });





    }


}