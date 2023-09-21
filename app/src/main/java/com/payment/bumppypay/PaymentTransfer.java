package com.payment.bumppypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class PaymentTransfer extends AppCompatActivity {
    String strupiid,strtTid,strorderid,stramount;
    double percentvalue, gstamount;
    float pecentge = 18;


    private static final int REQUEST_CODE_PHONEPE = 1002;
    private static final int REQUEST_CODE_PAYTM = 1003;
    private static final int REQUEST_CODE_GOOGLE_PAY = 1001;
    private static final int REQUEST_CODE_UPI_PAYMENT = 0;
    LinearLayout creditCardLayout,debitCardLayout,netbanking,phonepe,gpay,paytm,otherupi;
    String strAppName,strAmount,strUpiId,strTransactionId,strNotes,strCurrency;
    ImageView app_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_transfer);
        
        creditCardLayout=findViewById(R.id.creditCardLayout);
        debitCardLayout=findViewById(R.id.debitCardLayout);
        netbanking=findViewById(R.id.netbanking);
        phonepe=findViewById(R.id.phonepe);
        gpay=findViewById(R.id.gpay);
        paytm=findViewById(R.id.paytm);
        otherupi=findViewById(R.id.otherupi);
        app_logo=findViewById(R.id.app_logo);


            Intent i= getIntent();
            strAmount=i.getExtras().getString("trAm");
            strAppName=i.getExtras().getString("app_id");
            strUpiId=i.getExtras().getString("trUpiId");
            strTransactionId=i.getExtras().getString("trId");
            strNotes=i.getExtras().getString("trNotes");
            strCurrency=i.getExtras().getString("trcur");
            if(i.getExtras().getString("trOrId")!=null){
                strorderid=i.getExtras().getString("trOrId");
            }

       /* creditCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 //             visibilty gone or visible
//              mode.setText("Transaction by Credit Card : ");
                strchannel_id = "CC";
                percentvalue = (price / 100) * 1.25;
//              credit_card_choose.setVisibility(View.VISIBLE);
                gstamount = (percentvalue / 100) * 18;
                DecimalFormat df = new DecimalFormat("0.00");
                float temp;
                temp = pecentge / 100;
                charges.setText("+ 1.25% charges");
                float i = temp * parseFloat(a0mountSum);
                charges_amount.setText("₹" + String.valueOf(df.format(percentvalue)));
                gsttax.setText(String.valueOf(pecentge) + "% +GST");
                gstpercent.setText(String.valueOf(i));
                editamout.setText(": " + amountSum + ".0");
                total.setText(": " + String.valueOf(df.format(parseFloat(amountSum) + percentvalue + gstamount)));
                editamout.setText(String.valueOf("₹" + df.format(price)));
                gstpercent.setText(String.valueOf("₹" + df.format(gstamount)));
                amount.setText("₹ " + String.valueOf(df.format(parseFloat(amountSum) + percentvalue + gstamount)));
                stramountcc = String.valueOf(df.format(parseFloat(amountSum) + percentvalue + gstamount));
                paycreditcard.setText("Pay " + stramountcc);
            }
        });*/


        phonepe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingPhonepeUpi(strAppName, strUpiId, strNotes, strAmount,strCurrency);

            }
        });

        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingUpi(strAppName, strUpiId, strNotes, strAmount,strCurrency);

            }
        });
        
     paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent with the ACTION_VIEW action and the Paytm URI
                Uri paytmUri = Uri.parse("upi://pay")
                        .buildUpon()
                        .appendQueryParameter("pa", strUpiId.trim()) // Payee VPA (Virtual Payment Address)
                        .appendQueryParameter("pn", strAppName.trim()) // Payee name
                        .appendQueryParameter("mc", "000000") // Merchant code (optional)
                        .appendQueryParameter("tr", strorderid) // Transaction ID
                        .appendQueryParameter("tn", strNotes.trim()) // Transaction note
                        .appendQueryParameter("am", strAmount.trim()) // Transaction amount
                        .appendQueryParameter("mode", "04".trim()) // Payment mode (optional)
                        .appendQueryParameter("tid", strTransactionId) // Terminal ID (optional)
                        .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(paytmUri);
                intent.setPackage("net.one97.paytm");
                // Check if the user has the Paytm app installed
                try {
                    startActivityForResult(intent, REQUEST_CODE_PAYTM);
                } catch (Exception e) {
                    
                    Toast.makeText(PaymentTransfer.this, "PayTm app is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
     otherupi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUsingOtherUpi(strAppName, strUpiId, strNotes, strAmount,strCurrency);

            }
        });
        
    }

    void payUsingUpi(String name, String upiId, String note, String amount,String strCurrency) {
        Log.e("main", "name" + name + "--up--" + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", strCurrency)
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        upiPayIntent.setPackage("com.google.android.apps.nbu.paisa.user");

        try {
            startActivity(upiPayIntent);
        } catch (ActivityNotFoundException e) {
            // Google Pay app is not installed
            Toast.makeText(this, "Google Pay app is not installed", Toast.LENGTH_SHORT).show();
        }
        //------------
    }

    void payUsingPhonepeUpi(String name, String upiId, String note, String amount,String strCurrency) {
        Log.e("main", "name" + name + "--up--" + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", strCurrency)
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        upiPayIntent.setPackage("com.phonepe.app");

        try {
            startActivity(upiPayIntent);
        } catch (ActivityNotFoundException e) {
            // Google Pay app is not installed
            Toast.makeText(this, "PhonePe app is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    void payUsingOtherUpi(String name, String upiId, String note, String amount,String strCurrency) {
        Log.e("main", "name" + name + "--up--" + "--" + note + "--" + amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", strCurrency)
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, REQUEST_CODE_UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app Found", Toast.LENGTH_SHORT).show();
        }
    }

}