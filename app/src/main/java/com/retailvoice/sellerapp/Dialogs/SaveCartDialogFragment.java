package com.retailvoice.sellerapp.Dialogs;

import android.content.Intent;
import android.view.View;

import com.retailvoice.sellerapp.activities.SaveCartActivity;

public class SaveCartDialogFragment extends CustomDialogFragment {
    @Override
    public String getDescriptionText() {
        return "Save for later checkout?";
    }

     @Override
    public String getActionButtonText() {
        return "Save";
    }

    @Override
    public View.OnClickListener getOnActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SaveCartActivity.class));
                getActivity().finish();
            }
        };
    }
}
