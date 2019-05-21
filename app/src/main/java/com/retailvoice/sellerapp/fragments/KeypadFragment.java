package com.retailvoice.sellerapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.retailvoice.sellerapp.models.AdditionalCharge;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.activities.RegisterActivity;

public class KeypadFragment extends Fragment {

    int cost;
    MyApplication myApp;

    // View Components
    EditText itemName;
    TextView  itemCost;

    public KeypadFragment() {
        cost = 0;
        myApp = MyApplication.getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keypad, container, false);

        itemName = (EditText) view.findViewById(R.id.etItem);
        itemName.setInputType(InputType.TYPE_CLASS_TEXT);
        itemCost = (TextView) view.findViewById(R.id.itemCost);

        // Set OnClickListners to Numeric Buttons
        view.findViewById(R.id.button_0).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_1).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_2).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_3).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_4).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_5).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_6).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_7).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_8).setOnClickListener(keypadClickListner);
        view.findViewById(R.id.button_9).setOnClickListener(keypadClickListner);

        view.findViewById(R.id.button_clear).setOnClickListener(clearButtonClickListner);
        view.findViewById(R.id.button_clear).setOnLongClickListener(clearButtonLongClickListner);

        view.findViewById(R.id.button_add).setOnClickListener(addButtonClickListner);

        return view;
    }

    View.OnClickListener keypadClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
            Button b = (Button) v;
            int value = Integer.parseInt(b.getText().toString());
            cost = cost*10 + value;

            float realCost = (float) cost/100;
            itemCost.setText( getResources().getString(R.string.cart_price, realCost) );
        }
    };

    View.OnClickListener clearButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
            cost = 0;
            itemCost.setText(getResources().getString(R.string.cart_price, 0.0));

            //Toast.makeText(getContext(), "Long Press to Clear All", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnLongClickListener clearButtonLongClickListner = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            clearButtonClickListner.onClick(v);
            itemName.setText("");

            return true;
        }
    };

    View.OnClickListener addButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(itemName.getText().toString().length()<1){
                Toast.makeText(getContext(), "Enter Item Name", Toast.LENGTH_SHORT).show();
                return;
            }

            AdditionalCharge additionalCharge = new AdditionalCharge();
            additionalCharge.setName(itemName.getText().toString());
            additionalCharge.setPrice((float)cost/100);

            myApp.getCart().addAdditionalCharges(additionalCharge);

            ((RegisterActivity) getActivity()).updateCart();

            clearButtonLongClickListner.onLongClick(v);
        }
    };

}
