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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    public RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mContext = getApplicationContext();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /** Check SharedPreference
                 * Logined -> Pindah ke BaseActivity
                 * Belum UserObject -> Pindah ke LoginActivity
                 */
                if (SharedPreferenceUtils.getInstance(mContext).getBooleanValue("Logined", false)){
                /** Missing -> Token Checker */
                    Call<UserObject<Pasien>> call = retrofitInterface.loginAkun(
                            SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue("Email", ""),
                            SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue("Password", ""));
                    call.enqueue(new Callback<UserObject<Pasien>>() {
                                     @Override
                                     public void onResponse(Call<UserObject<Pasien>> call, Response<UserObject<Pasien>> response) {
                                         if (response.isSuccessful()) {
                                             SharedPreferenceUtils.getInstance(getApplicationContext()).setValue(response.body());
                                             SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Id", response.body().getId());
                                             SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Logined", true);
                                             if (response.body().getObject() != null) {
                                                 SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Role", response.body().getObject().getRole());
                                             }
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<UserObject<Pasien>> call, final Throwable t) {
                                     }
                                 });
                    startActivity(new Intent(getApplicationContext(), RiwayatPasienActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        }, 3000L); //3000 L = 3 detik
    }
}
