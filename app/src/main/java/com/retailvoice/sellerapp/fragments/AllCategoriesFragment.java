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
import com.retailvoice.sellerapp.adapters.CategoryAdapter;
import com.retailvoice.sellerapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesFragment extends Fragment {
    FloatingActionButton btnAddCategory;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_categories, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.caterogiesRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DataManager manager = DataManager.getSharedInstance(getContext());

        QueryEnumerator categories = null;
        try {
            categories = Category.getCategories(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        // 2
        List data = new ArrayList<>();
        for (QueryRow category : categories) {
            Document document = category.getDocument();
            Category model = ModelHelper.modelForDocument(document, Category.class);
            data.add(model);
        }

        // 3
        final CategoryAdapter adapter = new CategoryAdapter(data);
        mRecyclerView.setAdapter(adapter);

        btnAddCategory = (FloatingActionButton) view.findViewById(R.id.btnAdd);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new AddCategoryFragment());
                ft.commit();
            }
        });

        return view;
    }
}
