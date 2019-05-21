package com.retailvoice.sellerapp;

import com.retailvoice.sellerapp.models.AuthResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("authenticate")
    Call<AuthResult> authenticate(@Field("email") String name,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("users/add")
    Call<AuthResult> register(@Field("email") String email,
                              @Field("password") String password);
}
