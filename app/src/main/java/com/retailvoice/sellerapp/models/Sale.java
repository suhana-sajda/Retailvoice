package com.retailvoice.sellerapp.models;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;

import java.util.Date;
import java.util.Map;

public class Sale {

    private String _id;
    private String _rev;
    private String type;

    private Cart cart;

    private enum paymentMethod { CASH, CARD; }

    private Date dateTime;

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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public static Query getSales(Database database) {
        // 1
        View view = database.getView("app/sales");
        if (view.getMap() == null) {
            // 2
            view.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map document, Emitter emitter) {
                    // 4
                    if (document.get("type").equals("sale")) {
                        emitter.emit(document.get("_id"), null);
                    }
                }
            }, "1");
        }
        Query query = view.createQuery();
        return query;
    }
}
