package com.retailvoice.sellerapp;

import android.app.Application;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseOptions;
import com.couchbase.lite.Document;
import com.couchbase.lite.DocumentChange;
import com.couchbase.lite.Manager;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.retailvoice.sellerapp.models.Cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyApplication extends Application {
    public static final String TAG = "MyApplication.java";

    public static MyApplication myApp;
    private Cart mCart;

    private Boolean mEncryptionEnabled = false;

    private Manager manager;
    private Database database;
    private ArrayList<Document> accessDocuments = new ArrayList<Document>();

    private String mStorename;

    public static MyApplication getApplication() {

        if(myApp == null)
            return new MyApplication();

        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApp = this;

        try {
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
//                .name(Realm.DEFAULT_REALM_NAME)
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(realmConfiguration);

        mCart = new Cart();
    }

    private void startSession(String storename, String password, String newPassword) {
//        enableLogging();
//        installPrebuiltDb();
        mStorename = storename;
        openDatabase(storename, password, newPassword);
//        startReplication(username, password);
//        showApp();
//        startConflictLiveQuery();
    }

    private void openDatabase(String storename, String key, String newKey) {
        String dbname = storename;
        DatabaseOptions options = new DatabaseOptions();
        options.setCreate(true);

        if (mEncryptionEnabled) {
            options.setEncryptionKey(key);
        }

        Manager manager = null;
        try {
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            database = manager.openDatabase(dbname, options);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        if (newKey != null) {
            try {
                database.changeEncryptionKey(newKey);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }

        database.addChangeListener(new Database.ChangeListener() {
            @Override
            public void changed(Database.ChangeEvent event) {
                if(!event.isExternal()) {
                    return;
                }

                for(final DocumentChange change : event.getChanges()) {
                    if(!change.isCurrentRevision()) {
                        continue;
                    }

                    Document changedDoc = database.getExistingDocument(change.getDocumentId());
                    if(changedDoc == null) {
                        return;
                    }

                    String docType = (String) changedDoc.getProperty("type");
                    if(!docType.equals("task-list.user")) {
                        continue;
                    }

                    String username = (String) changedDoc.getProperty("username");
                    if(!username.equals(getStorename())) {
                        continue;
                    }

                    accessDocuments.add(changedDoc);
                    changedDoc.addChangeListener(new Document.ChangeListener() {
                        @Override
                        public void changed(Document.ChangeEvent event) {
                            Document changedDoc = database.getDocument(event.getChange().getDocumentId());
                            if (!changedDoc.isDeleted()) {
                                return;
                            }

                            try {
                                SavedRevision deletedRev = changedDoc.getLeafRevisions().get(0);
                                String listId = (String) ((HashMap<String, Object>) deletedRev.getProperty("taskList")).get("id");
                                Document listDoc = database.getExistingDocument(listId);
                                accessDocuments.remove(changedDoc);
                                listDoc.purge();
                                changedDoc.purge();
                            } catch (CouchbaseLiteException e) {
                                Log.e(TAG, "Failed to get deleted rev in document change listener");
                            }
                        }
                    });
                }
            }
        });
    }

    public String getStorename() {
        return mStorename;
    }

    public Cart getCart() {
        return mCart;
    }

    public void setCart(Cart mCart) {
        this.mCart = mCart;
    }
}