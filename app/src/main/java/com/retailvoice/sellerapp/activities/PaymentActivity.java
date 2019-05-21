package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;

public class PaymentActivity extends AppCompatActivity {

    MyApplication myApp;
    Button btnCash;
    Button btnCard;
    Button btnOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        myApp = MyApplication.getApplication();

        btnCash = (Button) findViewById(R.id.btnCash);
        btnCard = (Button) findViewById(R.id.btnRecordCardPayment);
        btnOther = (Button) findViewById(R.id.btnOther);

        btnCash.setOnClickListener(mCashButtonClickListner);
        btnCard.setOnClickListener(mCardButtonClickListner);
        btnOther.setOnClickListener(mOtherButtonClickListner);

        if( getSupportActionBar() != null ) {
            getSupportActionBar().setTitle("Payment");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener mCashButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent = new Intent(PaymentActivity.this, CashPaymentActivity.class);
            startActivity(mIntent);
        }
    };

    View.OnClickListener mCardButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener mOtherButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
