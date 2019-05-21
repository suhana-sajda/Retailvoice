package com.retailvoice.sellerapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.retailvoice.sellerapp.fragments.AllProductsFragment;
import com.retailvoice.sellerapp.R;

public class ProductsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new AllProductsFragment());
        ft.commit();
    }
}
