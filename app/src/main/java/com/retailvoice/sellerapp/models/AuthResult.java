package com.retailvoice.sellerapp.models;

import com.google.gson.annotations.SerializedName;

public class AuthResult {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;

    public AuthResult(Boolean success, String message, String userName, String id, String email) {
        this.success = success;
        this.message = message;
        this.name = userName;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

