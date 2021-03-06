package com.retailvoice.sellerapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.adapters.ProductsAdapter;
import com.retailvoice.sellerapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AllProductsFragment extends Fragment {
    private ProductsAdapter adapter;
    FloatingActionButton btnAddProduct;
    private RecyclerView recycler;
    List data;
    DataManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.productsRecycler);

        manager = DataManager.getSharedInstance(getContext());

        QueryEnumerator products = null;
        try {
            products = Product.getProducts(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        data = new ArrayList<>();
        for (QueryRow product : products) {
            Document document = product.getDocument();
            Product model = ModelHelper.modelForDocument(document, Product.class);
            data.add(model);
        }

        setupRecycler();

        btnAddProduct = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new AddProductFragment());
                ft.commit();
            }
        });

        return view;
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new ProductsAdapter(getActivity(), data, manager.database);
        recycler.setAdapter(adapter);
    }
}
