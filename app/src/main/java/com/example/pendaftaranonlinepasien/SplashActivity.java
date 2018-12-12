package com.example.pendaftaranonlinepasien;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.pendaftaranonlinepasien.API.POJO.Riwayat;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

public class SplashActivity extends AppCompatActivity {
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
