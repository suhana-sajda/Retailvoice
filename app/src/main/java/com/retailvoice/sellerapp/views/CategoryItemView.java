package com.retailvoice.sellerapp.views;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryItemView extends RelativeLayout {

    @BindView(R.id.text_category_title)
    TextView title;

    public CategoryItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_category, this);
        ButterKnife.bind(this);
    }

    public void bind(Category category) {
        title.setText(category.getName());
    }
}
