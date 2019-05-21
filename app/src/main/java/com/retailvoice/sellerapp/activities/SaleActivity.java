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
import com.retailvoice.sellerapp.adapters.SalesAdapter;
import com.retailvoice.sellerapp.models.Sale;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends BaseActivity {

    private SalesAdapter adapter;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        recycler = (RecyclerView) findViewById(R.id.recycler);

        setupRecycler();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        DataManager manager = DataManager.getSharedInstance(getApplicationContext());

        QueryEnumerator sales = null;
        try {
            sales = Sale.getSales(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        List data = new ArrayList<>();
        for (QueryRow sale : sales) {
            Document document = sale.getDocument();
            Sale model = ModelHelper.modelForDocument(document, Sale.class);
            data.add(model);
        }

        // create an empty adapter and add it to the recycler view
        adapter = new SalesAdapter(this, data);
        recycler.setAdapter(adapter);
    }
}
