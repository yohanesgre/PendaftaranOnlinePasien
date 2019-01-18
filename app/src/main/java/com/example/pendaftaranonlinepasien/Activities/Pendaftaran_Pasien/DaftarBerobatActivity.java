package com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarBerobatActivity extends BaseActivity {

    public static ContentViewHolder viewHolder;
    String dateFull;

    public class ContentViewHolder {
        @BindView(R.id.etNamaPasien)
        EditText etNama;
        @BindView(R.id.etTglKontrol)
        EditText etTanggalKontrol;
        @BindView(R.id.spinner_Penjamin)
        Spinner spinnerPenjamin;
        @BindView(R.id.spinner_Poli)
        Spinner spinnerPoli;
        @BindView(R.id.etJam)
        EditText etJam;
        @BindView(R.id.btn_Daftar)
        Button btnDaftar;

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
        View contentView = getLayoutInflater().inflate(R.layout.activity_daftar_berobat, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_3);
        this.setTitle("Daftar Berobat");
        final Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat formatFull = new SimpleDateFormat("yyyyMMdd");
        final DatePickerDialog  datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                viewHolder.etTanggalKontrol.setText(formatFull.format(newDate.getTime()));
                dateFull = formatFull.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        if (user.getObject()!=null){
            viewHolder.etNama.setText(user.getObject().getNama());
        } else {
            viewHolder.etNama.setText("Isi nama pada profile terlebih dahulu!");
        }
        viewHolder.etTanggalKontrol.setInputType(InputType.TYPE_NULL);
        viewHolder.etTanggalKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        viewHolder.etJam.setInputType(InputType.TYPE_NULL);
        viewHolder.etJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DaftarBerobatActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        viewHolder.etJam.setText( String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        viewHolder.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Berobat berobat = new Berobat();
                berobat.setTgl(viewHolder.etTanggalKontrol.getText().toString());
                berobat.setPoli(viewHolder.spinnerPoli.getSelectedItem().toString());
                berobat.setDokter("null");
                berobat.setJam(viewHolder.etJam.getText().toString());
                berobat.setPenjamin(viewHolder.spinnerPenjamin.getSelectedItem().toString());
                daftarKontrol(berobat);
            }
        });
    }

    private void daftarKontrol(Berobat berobat){
        Call<ArrayList<UserBerobat<Berobat>>> call = retrofitInterface.StoreUserBerobat(api_token, berobat);
        call.enqueue(new Callback<ArrayList<UserBerobat<Berobat>>>() {
            @Override
            public void onResponse(Call<ArrayList<UserBerobat<Berobat>>> call, Response<ArrayList<UserBerobat<Berobat>>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DaftarBerobatActivity.this, "Daftar kontrol berhasil!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DaftarBerobatActivity.this, HasilReservasiActivity.class);
                    intent.putExtra("Reservasi", response.body().get(0).getListObject().get(0).getReservasi());
                    intent.putExtra("Tanggal", response.body().get(0).getListObject().get(0).getTgl());
                    intent.putExtra("Poli", response.body().get(0).getListObject().get(0).getPoli());
                    intent.putExtra("Jam", response.body().get(0).getListObject().get(0).getJam());
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UserBerobat<Berobat>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy(){
        viewHolder.unbindButterKnife();
        super.onDestroy();
    }
    /*private String dayTranslate(String day){
            String result = null;
            switch (day){
                case "Monday":
                    result = "Senin";
                    break;
                case "Tuesday":
                    result = "Selasa";
                    break;
                case "Wednesday":
                    result = "Wednesday";
                    break;
                case "Thursday":
                    result = "Kamis";
                    break;
                case "Friday":
                    result = "Jumat";
                    break;
                case "Saturday":
                    result = "Sabtu";
                    break;
                case "Sunday":
                    result = "Minggu";
                    break;
            }
            return result;
     }*/
}
