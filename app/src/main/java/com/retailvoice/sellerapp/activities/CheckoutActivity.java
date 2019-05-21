package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.Dialogs.DeleteDialogFragment;
import com.retailvoice.sellerapp.Dialogs.SaveCartDialogFragment;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.adapters.CheckoutProductsAdapter;

public class CheckoutActivity extends AppCompatActivity {

    MyApplication myApp;
    CheckoutProductsAdapter mAdapter;

    TextView tvTotal;
    RecyclerView mProductView;
    Button mChargeButton;

    DataManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        myApp = MyApplication.getApplication();

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        mProductView = (RecyclerView) findViewById(R.id.productView);
        mChargeButton = (Button) findViewById(R.id.chargeButton);
        if( getSupportActionBar() != null ) {
            getSupportActionBar().setTitle("Checkout");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        manager = DataManager.getSharedInstance(getApplicationContext());

        mAdapter = new CheckoutProductsAdapter(manager.database);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mProductView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductView.setAdapter(mAdapter);

        float total = myApp.getCart().getTotal();
        tvTotal.setText(getResources().getString(R.string.cart_price, total));
        mChargeButton.setText("Charge "+ getResources().getString(R.string.cart_price, total));
        mChargeButton.setOnClickListener(mChargeButtonClickListener);
        if(total == 0)
            mChargeButton.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                new DeleteDialogFragment().show(getSupportFragmentManager(), "Delete");
                return true;
            case R.id.action_save:
                new SaveCartDialogFragment().show(getSupportFragmentManager(), "Save");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.checkout_activity_menu, menu);
        return true;
    }

    View.OnClickListener mChargeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent = new Intent(CheckoutActivity.this, PaymentActivity.class);
            startActivity(mIntent);
        }
    };

}
