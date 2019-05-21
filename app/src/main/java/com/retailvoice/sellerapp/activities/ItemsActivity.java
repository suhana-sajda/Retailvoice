package com.retailvoice.sellerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.retailvoice.sellerapp.R;

public class ItemsActivity extends BaseActivity {

    private Button btnProducts, btnCategories, btnDiscounts;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnProducts = (Button) findViewById(R.id.btnItems);
        btnCategories = (Button) findViewById(R.id.btnCategories);
        btnDiscounts = (Button) findViewById(R.id.btnDiscounts);

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

        btnDiscounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To be done
            }
        });
    }
}
