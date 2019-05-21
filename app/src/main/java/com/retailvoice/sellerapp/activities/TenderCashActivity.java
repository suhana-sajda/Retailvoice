package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;

public class TenderCashActivity extends BaseActivity {

    private TextView mTenderCashTextView;
    private EditText mCustomerEmailView;
    private View mCancelEmailView;

    public static String CASH_TENDERED = "cash_tendered";
    public static String TOTAL_BILL = "total_bill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_cash);

        Bundle b = getIntent().getExtras();

//        mTenderCashTextView = (TextView) findViewById(R.id.tv_tenderCash);
        mCustomerEmailView = (EditText) findViewById(R.id.email);
        mCancelEmailView = findViewById(R.id.no_receipt);

//        float cashTendered = b.getFloat(CASH_TENDERED);
//        float totalBill = b.getFloat(TOTAL_BILL);
//        float change = cashTendered-totalBill;

//        mTenderCashTextView.setText(getResources().getString(R.string.tender_cash, change, cashTendered));
    }

    @Override
    public void onResume() {
        super.onResume();
        mCancelEmailView.setOnClickListener(mOnCancelListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCancelEmailView.setOnClickListener(null);
    }

    View.OnClickListener mOnCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(),
                    RegisterActivity.class);
            startActivity(i);
            finish();
        }
    };

}
