package com.example.pendaftaranonlinepasien.Activities.Data_Pasien;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DataPasienActivity extends BaseActivity {

    Pasien dataPasien;
    ContentViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_data_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_1);
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
                    case 0:
                        dataPasien.setJK('L');
                        Toast.makeText(getBaseContext(), Integer.toString(viewHolder.rgJK.getCheckedRadioButtonId()) + " L", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        dataPasien.setJK('P');
                        Toast.makeText(getBaseContext(), Integer.toString(viewHolder.rgJK.getCheckedRadioButtonId()) + " P", Toast.LENGTH_SHORT).show();
                        break;
                }
                Toast.makeText(getBaseContext(), dataPasien.getNama(), Toast.LENGTH_SHORT).show();
            }
        });
    }

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
