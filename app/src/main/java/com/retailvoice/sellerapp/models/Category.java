package com.retailvoice.sellerapp.models;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;

import java.util.Map;

public class Category {

    public Category() {
    }

    private String _id;
    private String _rev;

    private String name;
    private String type;

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

    public Category(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Query getCategories(Database database) {
        // 1
        View view = database.getView("app/categories");
        if (view.getMap() == null) {
            // 2
            view.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map document, Emitter emitter) {
                    // 4
                    if (document.get("type").equals("category")) {
                        emitter.emit(document.get("_id"), null);
                    }
                }
            }, "1");
        }
        Query query = view.createQuery();
        return query;
    }
}
