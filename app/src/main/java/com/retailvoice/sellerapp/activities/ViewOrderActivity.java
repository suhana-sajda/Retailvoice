package com.retailvoice.sellerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.Dialogs.CheckoutSavedOrderDialogFragment;
import com.retailvoice.sellerapp.Dialogs.DeleteSavedOrderDialogFragment;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.adapters.CheckoutProductsAdapter;
import com.retailvoice.sellerapp.models.Cart;

public class ViewOrderActivity extends AppCompatActivity {

    CheckoutProductsAdapter mAdapter;
    public static Cart mCart;

    TextView tvTotal;
    RecyclerView mProductView;
    Button mChargeButton;
    DataManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        if(mCart == null)
            finish();

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        mProductView = (RecyclerView) findViewById(R.id.productView);
        mChargeButton = (Button) findViewById(R.id.chargeButton);
        if( getSupportActionBar() != null ) {
            getSupportActionBar().setTitle(mCart.getName());
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        manager = DataManager.getSharedInstance(getApplicationContext());

        mAdapter = new CheckoutProductsAdapter(manager.database);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mProductView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        mProductView.setAdapter(mAdapter);

        float total = mCart.getTotal();
        tvTotal.setText(getResources().getString(R.string.cart_price, total));
        mChargeButton.setOnClickListener(mChargeButtonClickListener);
        if(total == 0)
            mChargeButton.setEnabled(false);
    }

    View.OnClickListener mChargeButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: dialog to confirm delete previous cart.
            new CheckoutSavedOrderDialogFragment().show(getSupportFragmentManager(), "Confirm empty?");

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                new DeleteSavedOrderDialogFragment().show(getSupportFragmentManager(), "Delete Order");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCart = null;
    }
}
