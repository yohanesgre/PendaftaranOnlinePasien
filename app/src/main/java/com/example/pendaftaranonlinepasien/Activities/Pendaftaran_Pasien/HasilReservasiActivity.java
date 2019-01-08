package com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HasilReservasiActivity extends BaseActivity {
    ContentViewHolder viewHolder;

    public class ContentViewHolder {
        @BindView(R.id.tvTanggalBooking)
        TextView tvTanggalBooking;
        @BindView(R.id.tvPoli)
        TextView tvPoli;
        @BindView(R.id.tvJam)
        TextView tvJam;

        Unbinder unbinder;

        ContentViewHolder(View view) {
            unbinder = ButterKnife.bind(this, view);
        }
        protected void unbindButterKnife() {
            unbinder.unbind();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_hasil_reservasi, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        //mToolbar.setTitle("Hasil Reservasi Kontrol");
        Intent intent = getIntent();
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
        viewHolder.tvTanggalBooking.setText(dateStr);
        viewHolder.tvPoli.setText(intent.getStringExtra("Poli"));
        viewHolder.tvJam.setText(intent.getStringExtra("Jam"));
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
    public void onDestroy(){
        viewHolder.unbindButterKnife();
        super.onDestroy();
    }
}
