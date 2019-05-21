package com.retailvoice.sellerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.activities.ViewOrderActivity;
import com.retailvoice.sellerapp.models.Cart;

import java.util.List;

public class SavedOrdersAdapter extends RecyclerView.Adapter<SavedOrdersAdapter.SavedOrdersViewHolder> {

    Context mContext;
    List<Cart> data;

    public SavedOrdersAdapter(@NonNull Context context, @Nullable List<Cart> data) {
//        super(context, data);
        this.data = data;
        mContext = context;
    }

    @Override
    public SavedOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new SavedOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SavedOrdersViewHolder holder, int position) {
        final Cart cart = data.get(position);
        holder.orderName.setText(cart.getName());
        holder.items.setText("Items: " + cart.getItemCount());
        holder.totalCost.setText(mContext.getResources().getString(R.string.cart_price, cart.getTotal()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewOrderActivity.mCart = cart;
                Intent mIntent = new Intent(mContext, ViewOrderActivity.class);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SavedOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView orderName, items, totalCost;
        public View view;

        public SavedOrdersViewHolder(View view) {
            super(view);
            this.view = view;
            orderName = (TextView) view.findViewById(R.id.tv_order_name);
            items = (TextView) view.findViewById(R.id.tv_items);
            totalCost = (TextView) view.findViewById(R.id.total_cost);
        }
    }
}
