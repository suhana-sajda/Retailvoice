package com.retailvoice.sellerapp.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;

public abstract class CustomDialogFragment extends DialogFragment {

    TextView descriptionText;
    Button actionButton;
    Button alternateActionButton;

    View.OnClickListener onActionClickListener;
    View.OnClickListener onAlternateActionClickListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom);
        if(dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        descriptionText = (TextView) dialog.findViewById(R.id.description);
        actionButton = (Button) dialog.findViewById(R.id.action);
        alternateActionButton = (Button) dialog.findViewById(R.id.alternateAction);

        descriptionText.setText(getDescriptionText());
        actionButton.setText(getActionButtonText());
        alternateActionButton.setText(getAlternateActionButtonText());

        onActionClickListener = getOnActionClickListener();
        onAlternateActionClickListener = getOnAlternateActionClickListener();

        return dialog;
    }

    @Override
    public void onPause(){
        super.onPause();
        actionButton.setOnClickListener(null);
        alternateActionButton.setOnClickListener(null);
    }

    @Override
    public void onResume(){
        super.onResume();
        actionButton.setOnClickListener(onActionClickListener);
        alternateActionButton.setOnClickListener(onAlternateActionClickListener);
    }

    public abstract String getDescriptionText();

    public abstract String getActionButtonText();

    public String getAlternateActionButtonText(){
        return "Cancel";
    }

    public abstract View.OnClickListener getOnActionClickListener();

    public View.OnClickListener getOnAlternateActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        };
    }
}
