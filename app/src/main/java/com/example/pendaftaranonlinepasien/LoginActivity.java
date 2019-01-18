package com.example.pendaftaranonlinepasien;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.ListBerobatPasienActivity;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    boolean logined = false;
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

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = getApplicationContext();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login() {
        Log.d(TAG, "Login");

        btnLogin.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
        progressDialog.setContentView(R.layout.progressbar_dialog);
        progressDialog.show();

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        // TODO: Implement your own authentication logic here.
        // Retrofit login
        LoginRetrofit(email, password);
    }

    private void LoginRetrofit(final String email, final String password){
        Call<UserPasien<Object>> call = retrofitInterface.loginAkun(email, password);
        call.enqueue(new Callback<UserPasien<Object>>() {
            @Override
            public void onResponse(Call<UserPasien<Object>> call, Response<UserPasien<Object>> response) {
                if (response.isSuccessful()) {
                    SharedPreferenceUtils.getInstance(mContext).setValue("Logined", true);
                    SharedPreferenceUtils.getInstance(mContext).setValue("api_token", response.body().getApi_token());
                    getProfile();
                }
            }
            @Override
            public void onFailure(Call<UserPasien<Object>> call, final Throwable t) {
                Toast.makeText(LoginActivity.this, "1" + t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                btnLogin.setEnabled(true);
            }
        });
    }

    private void getProfile(){
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
                    progressDialog.dismiss();
                    btnLogin.setEnabled(true);
                    onLoginSuccess();
                    Intent mAIntent = new Intent(LoginActivity.this, ListBerobatPasienActivity.class);
                    startActivity(mAIntent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserPasien<Pasien>>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "2"+ t.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                btnLogin.setEnabled(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the BaseActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "Login successed", Toast.LENGTH_SHORT).show();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }
}
