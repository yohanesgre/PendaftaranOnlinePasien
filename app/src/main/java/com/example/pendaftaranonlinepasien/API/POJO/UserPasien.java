package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UserPasien<T> {

    @SerializedName("id")
    int id;
    @SerializedName("email")
    String email;
    @SerializedName("api_token")
    String api_token;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName(value = "profile", alternate = {"object"})
    T object;



    // Getter Attribute
    public T getObject(){
        return object;
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getApi_token() {
        return api_token;
    }

    // Setter Attribute
    public void setEmail(String email) {
        this.email = email;
    }
}
