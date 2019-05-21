package com.retailvoice.sellerapp.Dialogs;

import android.view.View;

public class DeleteSavedOrderDialogFragment extends CustomDialogFragment {

    @Override
    public String getDescriptionText() {
        return "Delete this Order ?";
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
    public View.OnClickListener getOnAlternateActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Realm realm = RealmController.with(getActivity()).getRealm();
//                realm.beginTransaction();
//                ViewOrderActivity.mCart.deleteFromRealm();
//                realm.commitTransaction();
//                getActivity().finish();
            }
        };
    }

    @Override
    public View.OnClickListener getOnActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        };
    }
}
