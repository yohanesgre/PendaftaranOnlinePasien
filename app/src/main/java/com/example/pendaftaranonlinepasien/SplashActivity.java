package com.example.pendaftaranonlinepasien;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.ListBerobatPasienActivity;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    public RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        final Context mContext = getApplicationContext();
        final boolean login = SharedPreferenceUtils.getInstance(mContext).getBooleanValue("Logined", false);
        if (login) {
            /** Missing -> Token Checker */
            Call<ArrayList<UserPasien<Pasien>>> call = retrofitInterface.getUserProfile(
                    SharedPreferenceUtils.getInstance(mContext).getStringValue("api_token", " ")
            );
            call.enqueue(new Callback<ArrayList<UserPasien<Pasien>>>() {
                @Override
                public void onResponse(Call<ArrayList<UserPasien<Pasien>>> call, Response<ArrayList<UserPasien<Pasien>>> response) {
                    if (response.isSuccessful()) {
                        SharedPreferenceUtils.getInstance(mContext).setValue(response.body().get(0));
                        if (response.body().get(0).getObject() != null){
                            SharedPreferenceUtils.getInstance(mContext).setValue("User_Id", response.body().get(0).getObject().getIdUser());
                            SharedPreferenceUtils.getInstance(mContext).setValue("Role", response.body().get(0).getObject().getRole());
                        }
                        startActivity(new Intent(mContext, ListBerobatPasienActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<UserPasien<Pasien>>> call, Throwable t) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                }
            });
        }
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                }
            }, 2000L);
        }
    }
}
