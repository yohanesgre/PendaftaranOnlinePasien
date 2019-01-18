package com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilReservasiActivity extends AppCompatActivity {

    @BindView(R.id.tvReservasi)
    TextView tvReservasi;
    @BindView(R.id.tvTanggalBooking)
    TextView tvTanggalBooking;
    @BindView(R.id.tvPoli)
    TextView tvPoli;
    @BindView(R.id.tvJam)
    TextView tvJam;
    
    private RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    private Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_reservasi);
        ButterKnife.bind(this);
        appContext = getApplicationContext();
        Intent intent = getIntent();
        tvReservasi.setText(intent.getStringExtra("Reservasi"));
        String dateStr = intent.getStringExtra("Tanggal");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = new Date();
            dateStr = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvTanggalBooking.setText(dateStr);
        tvPoli.setText(intent.getStringExtra("Poli"));
        tvJam.setText(intent.getStringExtra("Jam"));
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public void onDestroy(){
        unbindButterKnife();
        super.onDestroy();
    }*/
}
