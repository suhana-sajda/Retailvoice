package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Sale;

import java.util.Date;

public class CashPaymentActivity extends AppCompatActivity {

    MyApplication myApp;
    Button tenderButton, recieptButton;
    EditText mSearchTender;
    TextView tvTenderCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_payment);

        myApp = MyApplication.getApplication();

        mSearchTender = (EditText) findViewById(R.id.searchTender);

        tenderButton = (Button) findViewById(R.id.btnTender);

        tvTenderCash = (TextView) findViewById(R.id.tv_tenderCash);

        recieptButton = (Button) findViewById(R.id.btnReciept);



        tenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mSearchTender.getText().toString().length() < 1){
                    Toast.makeText(CashPaymentActivity.this, "Enter paid cash", Toast.LENGTH_SHORT).show();
                    return;
                }

                Sale sale = new Sale();
                sale.setCart(myApp.getCart());
                sale.setDateTime(new Date());
                sale.setType("sale");

                float cashTendered = Float.parseFloat(mSearchTender.getText().toString());
                float totalBill = myApp.getCart().getTotal();
                float change = cashTendered - totalBill;

                ModelHelper.save(DataManager.getSharedInstance(getApplicationContext()).database, sale, null);

                tvTenderCash.setVisibility(View.VISIBLE);

                tvTenderCash.setText(getResources().getString(R.string.tender_cash, change));
                tenderButton.setVisibility(View.GONE);
                recieptButton.setVisibility(View.VISIBLE);
            }
        });

        recieptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CashPaymentActivity.this, TenderCashActivity.class);
//                Bundle b = new Bundle();
//                b.putFloat(TenderCashActivity.CASH_TENDERED, Float.parseFloat(mSearchTender.getText().toString()));
//                b.putFloat(TenderCashActivity.TOTAL_BILL, myApp.getCart().getTotal());
//                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                myApp.getCart().emptyCart();
                finish();
            }
        });
    }
}
