package com.retailvoice.sellerapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;

public class SaveCartActivity extends AppCompatActivity {

    EditText nameText;
    Button saveBtn;
    MyApplication mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_cart);

//        final Realm realm = RealmController.with(this).getRealm();
        mApp = MyApplication.getApplication();

        if( getSupportActionBar() != null ) {
            getSupportActionBar().setTitle("Checkout");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        nameText = (EditText) findViewById(R.id.cartName);
        saveBtn = (Button) findViewById(R.id.save_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.getCart().setName(nameText.getText().toString());
                mApp.getCart().setType("cart");

                ModelHelper.save(DataManager.getSharedInstance(getApplicationContext()).database, mApp.getCart(), null);
                finish();
            }
        });
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
}
