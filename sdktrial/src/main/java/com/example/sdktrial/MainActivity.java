package com.example.sdktrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.payment.bumppypay.PaymentTransfer;

public class MainActivity extends AppCompatActivity {
TextView txtClick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtClick=findViewById(R.id.txtClick);


        txtClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, PaymentTransfer.class);
                i.putExtra("app_id",getResources().getString(R.string.app_name));
                i.putExtra("trId","000422");
                i.putExtra("trAm","1");
                i.putExtra("trUpiId","8979568087@ybl");
                i.putExtra("trNotes","This action add money into the Bumppy Payment wallet");
                i.putExtra("trcur","INR");
                i.putExtra("trOrId","123456789876543");
                startActivity(i);
            }
        });



    }
}