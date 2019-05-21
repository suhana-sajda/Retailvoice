package com.retailvoice.sellerapp.views;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockItemView extends RelativeLayout {

    @BindView(R.id.tvProductTitle)
    TextView title;

    public StockItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_product2, this);
        ButterKnife.bind(this);
    }

    public void bind(Product product) {
        title.setText(product.getName());
    }
}