package com.example.pendaftaranonlinepasien.API;

import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("api/register")
    @FormUrlEncoded
    Call<ResponseBody> registerAkun (@Field("email") String email, @Field("password") String password);

    @GET("api/profile")
    Call<ArrayList<UserPasien<Pasien>>> getUserProfile(@Header("Authorization") String api_token);

    @GET("api/berobat")
    Call<ArrayList<UserBerobat<Berobat>>> GetUserBerobat(@Header("Authorization") String api_token);

    @GET("api/berobat/{id}")
    Call<ArrayList<UserBerobat<Berobat>>> GetUserBerobatByID(@Header("Authorization") String api_token, @Path("id") int user_id);

    @POST("api/berobat/reservasi")
    @FormUrlEncoded
    Call<ArrayList<UserBerobat<Berobat>>> GetUserBerobatByReservasi(@Header("Authorization") String api_token, @Field("reservasi") String reservasi);

    @POST("api/berobat/store")
    Call<ArrayList<UserBerobat<Berobat>>> StoreUserBerobat(@Header("Authorization") String api_token, @Body Berobat berobat);

    @POST("api/profile/store")
    Call<ArrayList<UserPasien<Pasien>>> UpdateDataPasien (@Header("Authorization") String api_token, @Body Pasien pasien);

    @POST("api/login")
    @FormUrlEncoded
    Call<UserPasien<Object>> loginAkun(@Field("email") String email, @Field("password") String password);

    @POST("api/admin/get_user_by_norm")
    @FormUrlEncoded
    Call<Pasien> GetUserByNoRM (@Header("Authorization") String api_token, @Field("norm") int norm);

    @GET("api/admin/get_all_user")
    Call<ArrayList<UserPasien<Pasien>>> GetAllUser (@Header("Authorization") String api_token);


    @POST("api/admin/tambah_profile/{id}")
    Call<UserPasien<Pasien>> AdminUpdateDataPasien (@Path("id") int idUser, @Header("Authorization") String api_token, @Body Pasien pasien);

}
