package com.retailvoice.sellerapp.Dialogs;

import android.content.Intent;
import android.view.View;

import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.activities.CheckoutActivity;
import com.retailvoice.sellerapp.activities.ViewOrderActivity;

public class CheckoutSavedOrderDialogFragment extends CustomDialogFragment {
    @Override
    public String getDescriptionText() {
        return "This will empty the current cart. Proceed?";
    }

    @Override
    public String getActionButtonText() {
        return "Proceed";
    }

    @Override
    public View.OnClickListener getOnActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getApplication().setCart(ViewOrderActivity.mCart);
                Intent mIntent = new Intent(CheckoutSavedOrderDialogFragment.this.getActivity(), CheckoutActivity.class);
                startActivity(mIntent);
                getActivity().finish();
            }
        };
    }
}
