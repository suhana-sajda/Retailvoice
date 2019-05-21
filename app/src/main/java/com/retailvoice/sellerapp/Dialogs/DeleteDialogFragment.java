package com.retailvoice.sellerapp.Dialogs;

import android.view.View;
import android.view.View.OnClickListener;

import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.models.Cart;

public class DeleteDialogFragment extends CustomDialogFragment {

    @Override
    public String getDescriptionText() {
        return "Delete all items in Cart ?";
    }

    @Override
    public String getActionButtonText() {
        return "Cancel";
    }

    @Override
    public String getAlternateActionButtonText() {
        return "Delete";
    }

    @Override
    public OnClickListener getOnAlternateActionClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getApplication().setCart(new Cart());
                getActivity().finish();
            }
        };
    }

    @Override
    public OnClickListener getOnActionClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        };
    }
}
