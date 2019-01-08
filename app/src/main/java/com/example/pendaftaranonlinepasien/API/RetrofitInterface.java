package com.example.pendaftaranonlinepasien.API;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.Riwayat;
import com.example.pendaftaranonlinepasien.API.POJO.UserList;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/api/user")
    @FormUrlEncoded
    Call<ResponseBody> registerAkun (@Field("email") String email, @Field("password") String password);

    @POST("/api/user/login")
    @FormUrlEncoded
    Call<UserObject<Pasien>> loginAkun (@Field("email") String email, @Field("password") String password);

    @POST("/api/user/{id}/profile")
    Call<UserObject<Pasien>> UpdateDataPasien (@Path("id") int idUser, @Body Pasien pasien);

    @GET("/api/user/{id}/riwayart")
    Call<UserList<Riwayat>> getRiwayatPasien (@Path("id") int idUser);

    @POST("/api/user/{id}/berobat")
    @FormUrlEncoded
    Call<ResponseBody> DaftarBerobat (@Path("id") int idUser, @Field("tgl") String tgl, @Field("poli") String poli, @Field("dokter") String dokter, @Field("jam") String jam);
}
