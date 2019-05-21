package com.retailvoice.sellerapp;

/*
 * Copyright (c) 2016 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import android.graphics.Bitmap;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.UnsavedRevision;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public class ModelHelper {

  public static void save(Database database, Object object, Bitmap image) {
    ObjectMapper m = new ObjectMapper();
    Map<String, Object> props = m.convertValue(object, Map.class);
    String id = (String) props.get("_id");

    Document document;
    if (id == null) {
      document = database.createDocument();
    } else {
      document = database.getExistingDocument(id);
      if (document == null) {
        document = database.getDocument(id);
      } else {
        props.put("_rev", document.getProperty("_rev"));
      }
    }

    try {
      document.putProperties(props);
    } catch (CouchbaseLiteException e) {
      e.printStackTrace();
    }

    if(image != null) {

      UnsavedRevision revision = document.createRevision();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      image.compress(Bitmap.CompressFormat.JPEG, 50, out);
      ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
      revision.setAttachment("photo", "image/jpg", in);
      try {
        revision.save();
      } catch (CouchbaseLiteException e) {
        e.printStackTrace();
      }
    }
  }

  public static <T> T modelForDocument(Document document, Class<T> aClass) {
    ObjectMapper m = new ObjectMapper();
    return m.convertValue(document.getProperties(), aClass);
  }

}
