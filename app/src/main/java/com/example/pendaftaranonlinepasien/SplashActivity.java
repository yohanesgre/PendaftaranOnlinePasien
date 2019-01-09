package com.example.pendaftaranonlinepasien;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.Riwayat;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        if (SharedPreferenceUtils.getInstance(mContext).getBooleanValue("Logined", false)) {
            /** Missing -> Token Checker */
            Call<UserObject<Pasien>> call = retrofitInterface.loginAkun(
                    SharedPreferenceUtils.getInstance(mContext).getStringValue("Email", ""),
                    SharedPreferenceUtils.getInstance(mContext).getStringValue("Password", ""));
            call.enqueue(new Callback<UserObject<Pasien>>() {
                @Override
                public void onResponse(Call<UserObject<Pasien>> call, Response<UserObject<Pasien>> response) {
                    if (response.isSuccessful()) {
                        SharedPreferenceUtils.getInstance(mContext).setValue(response.body());
                        SharedPreferenceUtils.getInstance(mContext).setValue("Id", response.body().getId());
                        SharedPreferenceUtils.getInstance(mContext).setValue("Logined", true);
                        if (response.body().getObject() != null)
                            SharedPreferenceUtils.getInstance(mContext).setValue("Id_User", response.body().getObject().getIdUser());
                            SharedPreferenceUtils.getInstance(mContext).setValue("Role", response.body().getObject().getRole());
                        startActivity(new Intent(mContext, RiwayatPasienActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<UserObject<Pasien>> call, Throwable t) {
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
