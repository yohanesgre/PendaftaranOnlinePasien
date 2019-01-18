package com.example.pendaftaranonlinepasien.Activities.Admin_Menu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDataPasienActivity extends AppCompatActivity {
    private RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    private Context appContext;
    private UserPasien<Pasien> user;
    private String api_token;
    Pasien dataPasien;
    UserPasien<Pasien> dataUser;
    @BindView(R.id.etNamaPasien)
    EditText etNamaPasien;
    @BindView(R.id.etTTL)
    EditText etTTL;
    @BindView(R.id.etNIK)
    EditText etNIK;
    @BindView(R.id.etKerja)
    EditText etKerja;
    @BindView(R.id.etAlamat)
    EditText etAlamat;
    @BindView(R.id.etHP)
    EditText etHP;
    @BindView(R.id.etNamaIbu)
    EditText etNamaIbu;
    @BindView(R.id.rg_JK)
    RadioGroup rgJK;
    @BindView(R.id.rg_JK_Laki)
    RadioButton rbLK;
    @BindView(R.id.rg_JK_Perempuan)
    RadioButton rbP;
    @BindView(R.id.btn_Update_Pasien)
    Button btnUpdatePasien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_pasien);
        ButterKnife.bind(this);
        appContext = getApplicationContext();
        api_token = SharedPreferenceUtils.getInstance(appContext).getStringValue("api_token", "");
        if (SharedPreferenceUtils.getInstance(appContext).getUserProfileValue() != null)
            user = SharedPreferenceUtils.getInstance(appContext).getUserProfileValue();
        final Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat formatFull = new SimpleDateFormat("dd-MM-yyyy");
        final DatePickerDialog  datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etTTL.setText(formatFull.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        etTTL.setInputType(InputType.TYPE_NULL);
        etTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        initDataPasien();
        initButtonListener();
    }

    private void initButtonListener(){
        btnUpdatePasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPasien = new Pasien();
                dataPasien.setNama(etNamaPasien.getText().toString());
                dataPasien.setTtl(etTTL.getText().toString());
                dataPasien.setNik(etNIK.getText().toString());
                dataPasien.setKerja(etKerja.getText().toString());
                dataPasien.setAlamat(etAlamat.getText().toString());
                dataPasien.setHp(etHP.getText().toString());
                dataPasien.setIbu(etNamaIbu.getText().toString());
                switch (rgJK.getCheckedRadioButtonId()){
                    case R.id.rg_JK_Laki:
                        dataPasien.setJK("L");
                        break;
                    case R.id.rg_JK_Perempuan:
                        dataPasien.setJK("P");
                        break;
                }
                dataPasien.setRole("Pasien");
                updateDataPasien(dataPasien);
            }
        });
    }

    private void updateDataPasien(Pasien dataPasien_){
        Call<ArrayList<UserPasien<Pasien>>> call = retrofitInterface.UpdateDataPasien(api_token, dataPasien_);
        call.enqueue(new Callback<ArrayList<UserPasien<Pasien>>>() {
            @Override
            public void onResponse(Call<ArrayList<UserPasien<Pasien>>> call, Response<ArrayList<UserPasien<Pasien>>> response) {
                SharedPreferenceUtils.getInstance(getApplicationContext()).setValue(response.body().get(0));
                Toast.makeText(AdminDataPasienActivity.this, "Update Data Pasien berhasil!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<UserPasien<Pasien>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataPasien(){
        if (user.getObject() != null)
            if (user.getObject().getRole() == "Admin"){
                int mUser_id = getIntent().getIntExtra("Id_user", 0);
                if (mUser_id > 0){
                    Pasien bundlePasien;
                    if ((Pasien) getIntent().getSerializableExtra("Profile_Pasien") != null) {
                        bundlePasien = (Pasien) getIntent().getSerializableExtra("Profile_Pasien");
                        etNamaPasien.setText(bundlePasien.getNama());
                        etTTL.setText(bundlePasien.getTtl());
                        etNIK.setText(bundlePasien.getNik());
                        etKerja.setText(bundlePasien.getKerja());
                        etAlamat.setText(bundlePasien.getAlamat());
                        etHP.setText(bundlePasien.getHp());
                        etNamaIbu.setText(bundlePasien.getIbu());
                        rgJK.clearCheck();
                        switch (bundlePasien.getJK()){
                            case "L":
                                rbLK.setChecked(true);
                                break;
                            case "P":
                                rbP.setChecked(true);
                                break;
                        }
                    }
                }
            } else
                dataUser = user;
        else
            return;
        etNamaPasien.setText(dataUser.getObject().getNama());
        etTTL.setText(dataUser.getObject().getTtl());
        etNIK.setText(dataUser.getObject().getNik());
        etKerja.setText(dataUser.getObject().getKerja());
        etAlamat.setText(dataUser.getObject().getAlamat());
        etHP.setText(dataUser.getObject().getHp());
        etNamaIbu.setText(dataUser.getObject().getIbu());
        rgJK.clearCheck();
        switch (dataUser.getObject().getJK()){
            case "L":
                rbLK.setChecked(true);
                break;
            case "P":
                rbP.setChecked(true);
                break;
                default:
                    rbLK.setChecked(true);
                    break;
        }
    }

    @Override
    public void onBackPressed() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", etNamaPasien.getText());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            } else {
                super.onBackPressed();
            }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
