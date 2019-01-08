package com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKontrolActivity extends BaseActivity {

    public static ContentViewHolder viewHolder;
    UserObject<Pasien> user;

    DialogFragment newFragment;
    static String dateFull;

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
        View contentView = getLayoutInflater().inflate(R.layout.activity_daftar_kontrol, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_3);
        //mToolbar.setTitle("Daftar Control");
        newFragment = new DatePickerFragment();
        user = SharedPreferenceUtils.getInstance(getApplicationContext()).getUserProfileValue();
        if (user.getObject()!=null){
            viewHolder.etNama.setText(user.getObject().getNama());
        } else {
            viewHolder.etNama.setText("Isi nama pada profile terlebih dahulu!");
        }
        viewHolder.etTanggalKontrol.setInputType(InputType.TYPE_NULL);
        viewHolder.etTanggalKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getSupportFragmentManager(), "DatePicker");
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
                mTimePicker = new TimePickerDialog(DaftarKontrolActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        viewHolder.etJam.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        viewHolder.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarKontrol();
                Intent intent = new Intent(DaftarKontrolActivity.this, HasilReservasiActivity.class);
                intent.putExtra("Tanggal", dateFull.toString());
                intent.putExtra("Poli", viewHolder.spinnerPoli.getSelectedItem().toString());
                intent.putExtra("Jam", viewHolder.etJam.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void daftarKontrol(){
        Call<ResponseBody> call = retrofitInterface.DaftarBerobat(SharedPreferenceUtils.getInstance(getApplicationContext()).getUserProfileValue().getId(),
                dateFull, viewHolder.spinnerPoli.getSelectedItem().toString(), "", viewHolder.etJam.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(DaftarKontrolActivity.this, "Daftar kontrol berhasil!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

    public static void setDateFull(String date){
        dateFull = date;
    }

    /**
     * TODO: Class DatePickerFragment
     */
    public static class DatePickerFragment extends DialogFragment {
        final Calendar c = Calendar.getInstance();
        String dateFull;
        ContentViewHolder viewHolder;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                SimpleDateFormat formatFull = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
                Date date = c.getTime();
                String dateResult = formatFull.format(date);
                String dayOfWeek = dayTranslate(formatDay.format(date));
                dateFull = dateResult;
                Toast.makeText(getActivity(), "selected date: " + dateFull,
                        Toast.LENGTH_SHORT).show();
                setDateFull(dateFull);
                DaftarKontrolActivity.viewHolder.etTanggalKontrol.setText(dateFull);
            }
        };

        private String dayTranslate(String day){
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
        }
    }
}
