package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserBerobat<T> {

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
    @SerializedName(value = "berobat", alternate = {"list"})
    ArrayList<T> list = null;

    // Getter Attribute
    public ArrayList<T> getListObject() {
        return list;
    }
    public T getFirstListObject(){
        return getListObject().get(0);
    }
    public int getId() {
        return id;
    }
    public String getApi_token() {
        return api_token;
    }
}
