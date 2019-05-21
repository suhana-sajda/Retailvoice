package com.retailvoice.sellerapp.models;

import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart {
    private String _id;
    private String _rev;
    private String type;

    private String name;

    private List<CartItem> items;

    private List<AdditionalCharge> additionalCharges;

    private int itemCount;

    private float total;

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

    public Cart() {
        super();
        this.name = "";
        this.items = new ArrayList();
        this.additionalCharges = new ArrayList();
        this.total = 0;
        this.itemCount = 0;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItems (CartItem item) {
//        this.items = new RealmList<>();
        this.items.add(item);
        this.itemCount = this.itemCount + item.getItemCount();
        this.total = this.total + item.getItem().getPrice() * item.getItemCount();
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void increateQuantity(Product item) {
        for(int i=0; i<items.size(); i++){
            if(items.get(i).getItem() == item) {
                CartItem cartitem = items.get(i);
                cartitem.setItemCount(cartitem.getItemCount() + 1);
                Log.d("itemCount", this.itemCount + "");
                this.itemCount = this.itemCount + 1;
                Log.d("itemCount", this.itemCount + "");
                Log.d("total", this.total + "");
                this.total = this.total + cartitem.getItem().getPrice();
                Log.d("total", this.total + "");
                break;
            }
        }
    }

    public void decreaseQuantity(Product item) {
        for(int i=0; i<items.size(); i++){
            if(items.get(i).getItem() == item) {
                CartItem cartitem = items.get(i);
                cartitem.setItemCount(cartitem.getItemCount() - 1);
                this.itemCount = this.itemCount - 1;
                this.total = this.total - cartitem.getItem().getPrice();
                break;
            }
        }
    }

    public void emptyCart() {
        this.items.clear();
        this.additionalCharges.clear();
        this.total = 0;
        this.itemCount = 0;
    }

    public List<AdditionalCharge> getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(List<AdditionalCharge> additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public void addAdditionalCharges (AdditionalCharge additionalCharge) {
        this.additionalCharges.add(additionalCharge);
        this.itemCount = this.itemCount + 1;
        this.total = this.total + additionalCharge.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Query getCarts(Database database) {
        // 1
        View view = database.getView("app/carts");
        if (view.getMap() == null) {
            // 2
            view.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map document, Emitter emitter) {
                    // 4
                    if (document.get("type").equals("cart")) {
                        emitter.emit(document.get("_id"), null);
                    }
                }
            }, "1");
        }
        Query query = view.createQuery();
        return query;
    }
}
