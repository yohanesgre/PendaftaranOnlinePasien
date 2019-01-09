package com.example.pendaftaranonlinepasien.Activities.Data_Pasien;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPasienActivity extends BaseActivity {

    Pasien dataPasien;
    UserObject<Pasien> dataUser;
    ContentViewHolder viewHolder;

    protected class ContentViewHolder{
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

        Unbinder unbinder;
        ContentViewHolder(View view){
            unbinder = ButterKnife.bind(this, view);
        }
        protected void unbindButterKnife(){
            unbinder.unbind();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_data_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_1);
        initDataPasien();
        initButtonListener();
    }

    private void initButtonListener(){
        viewHolder.btnUpdatePasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPasien = new Pasien();
                dataPasien.setNama(viewHolder.etNamaPasien.getText().toString());
                dataPasien.setTtl(viewHolder.etTTL.getText().toString());
                dataPasien.setNik(viewHolder.etNIK.getText().toString());
                dataPasien.setKerja(viewHolder.etKerja.getText().toString());
                dataPasien.setAlamat(viewHolder.etAlamat.getText().toString());
                dataPasien.setHp(viewHolder.etHP.getText().toString());
                dataPasien.setIbu(viewHolder.etNamaIbu.getText().toString());
                switch (viewHolder.rgJK.getCheckedRadioButtonId()){
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
        Call<UserObject<Pasien>> call = retrofitInterface.UpdateDataPasien(SharedPreferenceUtils.getInstance(getApplicationContext()).getUserProfileValue().getId(), dataPasien_);
        call.enqueue(new Callback<UserObject<Pasien>>() {
            @Override
            public void onResponse(Call<UserObject<Pasien>> call, Response<UserObject<Pasien>> response) {
                SharedPreferenceUtils.getInstance(getApplicationContext()).setValue(response.body());
                Toast.makeText(DataPasienActivity.this, "Update Data Pasien berhasil!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserObject<Pasien>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataPasien(){
        if (user.getObject() != null)
            dataUser = user;
        else
            return;
        viewHolder.etNamaPasien.setText(dataUser.getObject().getNama());
        viewHolder.etTTL.setText(dataUser.getObject().getTtl());
        viewHolder.etNIK.setText(dataUser.getObject().getNik());
        viewHolder.etKerja.setText(dataUser.getObject().getKerja());
        viewHolder.etAlamat.setText(dataUser.getObject().getAlamat());
        viewHolder.etHP.setText(dataUser.getObject().getHp());
        viewHolder.etNamaIbu.setText(dataUser.getObject().getIbu());
        viewHolder.rgJK.clearCheck();
        switch (dataUser.getObject().getJK()){
            case "L":
                viewHolder.rbLK.setChecked(true);
                break;
            case "P":
                viewHolder.rbP.setChecked(true);
                break;
                default:
                    viewHolder.rbLK.setChecked(true);
                    break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy(){
        viewHolder.unbindButterKnife();
        super.onDestroy();
    }
}
