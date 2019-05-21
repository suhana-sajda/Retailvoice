package com.retailvoice.sellerapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Sale;

import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.CardViewHolder>{

    private List<Sale> mSales;
    final Context context;
    private LayoutInflater inflater;

    public SalesAdapter(Context context, List<Sale> sales) {
        this.context = context;
        this.mSales = sales;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, final int position) {

        // get the article
        final Sale sale = mSales.get(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textTotal.setText("Rs." + sale.getCart().getTotal());
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        return mSales.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTotal;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_sale);
            textTotal = (TextView) itemView.findViewById(R.id.tv_total);
        }
    }

}
