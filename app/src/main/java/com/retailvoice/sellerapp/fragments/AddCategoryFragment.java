package com.retailvoice.sellerapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.retailvoice.sellerapp.DataManager;
import com.retailvoice.sellerapp.ModelHelper;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Category;

public class AddCategoryFragment extends Fragment {

    private FloatingActionButton done;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_category, parent, false);

        done = (FloatingActionButton) view.findViewById(R.id.btnDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editName = (EditText) view.findViewById(R.id.etName);

                Category category = new Category("category", editName.getText().toString());

                ModelHelper.save(DataManager.getSharedInstance(getContext()).database, category, null);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder, new AllCategoriesFragment());
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
}