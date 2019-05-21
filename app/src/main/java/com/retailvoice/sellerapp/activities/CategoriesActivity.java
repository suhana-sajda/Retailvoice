package com.retailvoice.sellerapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.retailvoice.sellerapp.fragments.AllCategoriesFragment;
import com.retailvoice.sellerapp.R;

public class CategoriesActivity  extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, new AllCategoriesFragment());
        ft.commit();
    }
}
