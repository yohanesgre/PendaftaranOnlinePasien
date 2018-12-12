package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserList<T> {

    @SerializedName("id")
    int id;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName(value = "list", alternate = {"berobat"})
    List<T> list;

    // Getter Attribute
    public List<T> getListObject() {
        return list;
    }
    public T getFirstListObject(){
        return getListObject().get(0);
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }

        // Setter Attribute
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
