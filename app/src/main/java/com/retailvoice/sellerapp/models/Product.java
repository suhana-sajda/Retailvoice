package com.retailvoice.sellerapp.models;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class Product {
    private String _id;

    private String _rev;

    private String name;

    private String description;

    @JsonIgnore
    private String _attachments;

    private int inventory;

    private String categoryId;

    private float price;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get_attachments() {
        return _attachments;
    }

    public void set_attachments(String _attachments) {
        this._attachments = _attachments;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static Query getProducts(Database database) {
        // 1
        View view = database.getView("app/products");
        if (view.getMap() == null) {
            // 2
            view.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map document, Emitter emitter) {
                    // 4
                    if (document.get("type").equals("product")) {
                        emitter.emit(document.get("_id"), null);
                    }
                }
            }, "1");
        }
        Query query = view.createQuery();
        return query;
    }
}
