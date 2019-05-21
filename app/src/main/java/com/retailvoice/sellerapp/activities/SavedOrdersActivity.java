package com.retailvoice.sellerapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.adapters.SavedOrdersAdapter;
import com.retailvoice.sellerapp.models.Cart;

import java.util.ArrayList;
import java.util.List;

public class SavedOrdersActivity extends BaseActivity {

    RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_orders);

        DataManager manager = DataManager.getSharedInstance(getApplicationContext());

        QueryEnumerator carts = null;
        try {
            carts = Cart.getCarts(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        List data = new ArrayList<>();
        for (QueryRow cart : carts) {
            Document document = cart.getDocument();
            Cart model = ModelHelper.modelForDocument(document, Cart.class);
            data.add(model);
        }

        recycler = (RecyclerView) findViewById(R.id.recycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        SavedOrdersAdapter mAdapter = new SavedOrdersAdapter(this, data);
        recycler.setAdapter(mAdapter);
    }

}
