package com.softcodeinfotech.easypick;

import com.softcodeinfotech.easypick.response.ContactusResponse;
import com.softcodeinfotech.easypick.response.LocationResponse;
import com.softcodeinfotech.easypick.response.QueryFormResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

 @Multipart
    @POST("property/easypick/contactus.php")
    Call<ContactusResponse> saveContact (
         @Part("name")RequestBody name,
         @Part("email")RequestBody email,
         @Part("mobile")RequestBody mobile,
         @Part("comment")RequestBody comment
         );


    @Multipart
    @POST("property/easypick/queryform.php")
    Call<QueryFormResponse> saveQuery (
            @Part("category")RequestBody category,
            @Part("picklocation")RequestBody picklocation,
            @Part("droplocation")RequestBody droplocation,
            @Part("comment")RequestBody comment
    );


    @Multipart
            @POST("property/easypick/getlocation.php")
    Call<LocationResponse> getLocation(
            @Part("location")RequestBody location
    );

   /* @Multipart
    @POST("property/new_user_registration.php")
    Call<NewUserRegistration> NewUserRegistrationCall(
            @Part("fullname") RequestBody fullname,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );*/
}
