package com.retailvoice.sellerapp.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Revision;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.AdditionalCharge;
import com.retailvoice.sellerapp.models.Cart;
import com.retailvoice.sellerapp.models.CartItem;
import com.retailvoice.sellerapp.models.Product;

import java.io.InputStream;

public class CheckoutProductsAdapter extends RecyclerView.Adapter<CheckoutProductsAdapter.CardViewHolder> {


    MyApplication myApp;
    Cart mCart;
    Database database;

    public CheckoutProductsAdapter(Database database) {
        myApp = MyApplication.getApplication();
        mCart = myApp.getCart();
        this.database = database;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if(position < mCart.getItems().size()) {
            final CartItem cartItem = mCart.getItems().get(position);
            final Product product = cartItem.getItem();

            holder.textTitle.setText(product.getName());
            holder.quantity.setText("QTY: " + cartItem.getItemCount());
            holder.price.setText(myApp.getApplicationContext().getResources().getString(R.string.cart_price, cartItem.getTotalPrice()) );
            holder.sku.setText("");

//            Glide.with(myApp.getApplicationContext())
//                    .load(product.getImageUri())
//                    .into(holder.product);

            Document document = database.getDocument(product.get_id());
            Revision revision = document.getCurrentRevision();
            Log.d("revision", revision.toString());
            Attachment attachment = revision.getAttachment("photo");
            if (attachment != null) {
                Log.d("image","not null");
                InputStream is = null;
                try {
                    is = attachment.getContent();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                Drawable drawable = Drawable.createFromStream(is, "photo");
                holder.product.setImageDrawable(drawable);
            }


        } else {
            final AdditionalCharge additionalCharge = mCart.getAdditionalCharges().get(position - mCart.getItems().size());
            holder.textTitle.setText(additionalCharge.getName());
            holder.price.setText(myApp.getApplicationContext().getResources().getString(R.string.cart_price, additionalCharge.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return mCart.getItems().size() + mCart.getAdditionalCharges().size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView price, quantity, sku;
        public ImageView product;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_product);
            textTitle = (TextView) itemView.findViewById(R.id.text_product_title);
//            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            product = (ImageView) itemView.findViewById(R.id.ivProduct);
            quantity = (TextView) itemView.findViewById(R.id.tvQty);
            sku = (TextView) itemView.findViewById(R.id.Sku);
        }
    }
}
