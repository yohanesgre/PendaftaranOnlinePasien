package com.example.pendaftaranonlinepasien;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    LoginActivity loginActivity = new LoginActivity();

    @BindView(R.id.input_email)
    EditText etEmail;
    @BindView(R.id.input_password)
    EditText etPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.link_login)
    TextView tvLogin;

    final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mContext=getApplicationContext();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarUser();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void daftarUser(){
        if (!validasiDaftar()){
            daftarGagal();
            return;
        }

        btnSignup.setEnabled(true);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL);
        progressDialog.setContentView(R.layout.progressbar_dialog);
        progressDialog.show();

        /*
            Post UserObject Register to Server
         */
        Call<ResponseBody> call = retrofitInterface.registerAkun(etEmail.getText().toString(), etPassword.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                progressDialog.dismiss();
                                daftarBerhasil();
                            }
                        }, 3000);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                daftarGagal();
                call.cancel();
            }
        });

    }

    private boolean validasiDaftar(){
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Masukkan email valid!");
            valid = false;
        } else
            etEmail.setError(null);

        if (password.isEmpty() || password.length() < 4){
            etPassword.setError("Password harus lebih dari 4!");
            valid = false;
        }else
            etPassword.setError(null);
        return valid;
    }

    public void daftarBerhasil() {
        btnSignup.setEnabled(true);
        Toast.makeText(getBaseContext(), "Pendaftaran akun berhasil!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void daftarGagal(){
        Toast.makeText(getBaseContext(), "Pendaftaran akun gagal! Ulangi lagi!", Toast.LENGTH_SHORT).show();
        btnSignup.setEnabled(true);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
