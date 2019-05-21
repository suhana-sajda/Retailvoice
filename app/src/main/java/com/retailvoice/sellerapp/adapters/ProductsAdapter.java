package com.retailvoice.sellerapp.adapters;

import android.content.Context;
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
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Product;

import java.io.InputStream;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.CardViewHolder> {

    final Context context;
    private LayoutInflater inflater;

    private List<Product> mProducts;
    Database database;

    public ProductsAdapter(Context context, List<Product> products, Database database) {
        this.context = context;
        this.mProducts = products;
        this.database = database;
    }

    // create new views (invoked by the layout manager)
    @Override
    public ProductsAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductsAdapter.CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, final int position) {

        // get the article
        final Product product = mProducts.get(position);
        // cast the generic view holder to our specific one
        final ProductsAdapter.CardViewHolder holder = (ProductsAdapter.CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textTitle.setText(product.getName());
        holder.price.setText("₹" + String.valueOf(product.getPrice()));
        holder.quantity.setText("QTY: " + String.valueOf(product.getInventory()));

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
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {
        return  mProducts.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView price, quantity;
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
        }
    }
}

