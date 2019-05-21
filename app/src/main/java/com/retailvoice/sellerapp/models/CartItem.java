package com.retailvoice.sellerapp.models;

public class CartItem {

    private String _id;
    private String _rev;
    private String type;

    private Product item;

    private int itemCount;
    private double totalPrice;

    public CartItem() {
        this.itemCount = 0;
    }

    public Product getItem() {
        return item;
    }

    public void setItem(Product item) {
        this.item = item;
        this.itemCount++;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getTotalPrice() {
        return itemCount * item.getPrice();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
