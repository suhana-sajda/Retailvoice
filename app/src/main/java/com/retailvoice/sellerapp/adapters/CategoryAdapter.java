package com.retailvoice.sellerapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retailvoice.sellerapp.R;
import com.retailvoice.sellerapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

  private List<Category> mCategories;
  private OnItemClickListener mOnItemClickListener;

  public interface OnItemClickListener {
    void OnClick(View view, int position);
  }

  public CategoryAdapter(List<Category> categories) {
    this.mCategories = categories;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View categoryView = inflater.inflate(R.layout.item_category, parent, false);
    return new ViewHolder(categoryView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    // 1
    Category category = mCategories.get(position);

    // 3
    holder.tvCategory.setText(category.getName());

    // 4
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnItemClickListener != null) {
          mOnItemClickListener.OnClick(v, position);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mCategories.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView tvCategory;
    private TextView mTag;

    public ViewHolder(View itemView) {
      super(itemView);
      tvCategory = (TextView) itemView.findViewById(R.id.text_category_title);
    }
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public List<Category> getmCategories() {
    return mCategories;
  }
}
