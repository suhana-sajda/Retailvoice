package com.retailvoice.sellerapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Revision;
import com.retailvoice.sellerapp.models.Cart;
import com.retailvoice.sellerapp.models.CartItem;
import com.retailvoice.sellerapp.MyApplication;
import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Product;
import com.retailvoice.sellerapp.views.StockItemView;
import com.retailvoice.sellerapp.activities.RegisterActivity;

import java.io.InputStream;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    Context context;
    MyApplication myApp;
    Cart cart;
    CartItem item;
    private List<Product> mProducts;
    Database database;

    public StockAdapter(Context context, List<Product> products, Database database) {
        this.context = context;
        this.mProducts = products;
        this.database = database;
        this.myApp = MyApplication.getApplication();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public TextView textTitle, textCategory, textPrice, textItemCount;
        public ImageView imageProduct;
        public Button buttonAdd, buttonPlus, buttonMinus;
        public LinearLayout viewButtons;

        public ViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_product);
            textTitle = (TextView) itemView.findViewById(R.id.tvProductTitle);
//            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
            textCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            imageProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
            textPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            buttonAdd = (Button) itemView.findViewById(R.id.btnAdd);
            buttonPlus = (Button) itemView.findViewById(R.id.btnPlus);
            buttonMinus = (Button) itemView.findViewById(R.id.btnMinus);
            textItemCount = (TextView) itemView.findViewById(R.id.tvItemCount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder vh = new ViewHolder(new StockItemView(viewGroup.getContext()));
        cart = myApp.getCart();
//        cart.setId((int) System.currentTimeMillis());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Product product = mProducts.get(position);
//        viewHolder.productItemView.bind(product);
        // cast the generic view holder to our specific one
        final StockAdapter.ViewHolder holder = (StockAdapter.ViewHolder) viewHolder;
        // set the title and the snippet
        holder.textTitle.setText(product.getName());
        holder.textCategory.setText("Qty: " + String.valueOf(product.getInventory()));
        holder.textPrice.setText("₹: " + String.valueOf(product.getPrice()));

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
            holder.imageProduct.setImageDrawable(drawable);
        }

//        Bitmap selectedImage = null;
//        try {
//            String imageUri = product.getImageUri();
//            if(imageUri != null)
//                selectedImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(product.getImageUri()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // Load the selected image into a preview
//        holder.imageProduct.setImageBitmap(selectedImage);

        if (holder.textItemCount.getVisibility() == View.VISIBLE) {
            holder.buttonPlus.setVisibility(View.GONE);
            holder.buttonMinus.setVisibility(View.GONE);
            holder.textItemCount.setVisibility(View.GONE);
            holder.buttonAdd.setVisibility(View.VISIBLE);
        }

        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttonPlus.setVisibility(View.VISIBLE);
                holder.buttonMinus.setVisibility(View.VISIBLE);
                holder.textItemCount.setVisibility(View.VISIBLE);
                holder.buttonAdd.setVisibility(View.GONE);

//                Cart cart = new Cart();
////                cart.Cart();
////                int cartId = RealmController.getInstance().getCart().size() + (int)System.currentTimeMillis();
//                cart.setId((int)System.currentTimeMillis());
                item = new CartItem();
                item.setItem(product);
                cart.addItems(item);
                ((RegisterActivity) context).updateCart(cart);
            }
        });

        holder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemCount = Integer.parseInt(holder.textItemCount.getText().toString());
                holder.textItemCount.setText(itemCount + 1 + "");
                cart.increateQuantity(product);
                ((RegisterActivity) context).updateCart(cart);
            }
        });

        holder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemCount = Integer.parseInt(holder.textItemCount.getText().toString());
                if (itemCount == 1) {
                    holder.buttonPlus.setVisibility(View.GONE);
                    holder.buttonMinus.setVisibility(View.GONE);
                    holder.textItemCount.setVisibility(View.GONE);
                    holder.buttonAdd.setVisibility(View.VISIBLE);
                    cart.decreaseQuantity(product);
                } else {
                    holder.textItemCount.setText(itemCount - 1 + "");
                    cart.decreaseQuantity(product);
                }
                ((RegisterActivity) context).updateCart(cart);
            }
        });
    }

    public int getItemCount() {
        return  mProducts.size();
    }
}
