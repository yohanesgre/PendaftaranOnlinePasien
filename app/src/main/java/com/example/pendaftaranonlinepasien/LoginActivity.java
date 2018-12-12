package com.example.pendaftaranonlinepasien;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;
import com.example.pendaftaranonlinepasien.Utils.DataFormatConverterUtils;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.link_signup)
    TextView linkRegister;
    ProgressDialog progressDialog;
    final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

         /*
            Post UserObject Register to Server
         */
    }
    public void login() {
        Log.d(TAG, "Login");

        btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // TODO: Implement your own authentication logic here.
        // Retrofit login

        Call<UserObject<Pasien>> call = retrofitInterface.loginAkun(email, password);
        call.enqueue(new Callback<UserObject<Pasien>>() {
            @Override
            public void onResponse(Call<UserObject<Pasien>> call, Response<UserObject<Pasien>> response) {
                if (response.isSuccessful()){
                    Gson gson = new Gson();
                    String text = gson.toJson(response.body());
                    SharedPreferenceUtils.getInstance(getApplicationContext()).setValue(response.body());
                    SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Id", response.body().getId());
                    SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Logined", true);
                    if (response.body().getObject() != null){
                        SharedPreferenceUtils.getInstance(getApplicationContext()).setValue("Role", response.body().getObject().getRole());
                    }
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    onLoginSuccess();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                } else {
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
            }

            @Override
            public void onFailure(Call<UserObject<Pasien>> call, final Throwable t) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                //onLoginSuccess();
                                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                                btnLogin.setEnabled(true);
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the BaseActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        Toast.makeText(LoginActivity.this, "Login successed", Toast.LENGTH_SHORT).show();
        Intent mAIntent = new Intent(LoginActivity.this, RiwayatPasienActivity.class);
        startActivity(mAIntent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }
}
