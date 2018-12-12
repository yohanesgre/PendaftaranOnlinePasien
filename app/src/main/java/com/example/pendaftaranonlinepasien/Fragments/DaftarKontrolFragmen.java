package com.example.pendaftaranonlinepasien.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pendaftaranonlinepasien.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaftarKontrolFragmen extends Fragment{

    DialogFragment newFragment;
    static String dateFull;
    String poli;
    String dokter;
    Context mContext;
    String penjamin;

    String[] listPoli = {"Poli 1", "Poli 2", "Poli 3"};
    String[] listDokter = {"Dokter 1", "Dokter 2", "Dokter 3"};
    public static EditText etTanggal;
    @BindView(R.id.rg_penjamin)
    RadioGroup radioGroup;
    @BindView(R.id.spinner_poli)
    Spinner spinnerPoli;
    @BindView(R.id.spinner_dokter)
    Spinner spinnerDokter;
    @BindView(R.id.btn_Daftar)
    Button btnDaftar;

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        getActivity().setTitle("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dfkontrol, container, false);;
        etTanggal = (EditText)view.findViewById(R.id.et_tanggalKontrol);
        newFragment = new DatePickerFragment();
        ButterKnife.bind(this, view);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
               switch (checkedId){
                   case R.id.rb_bpjs:
                       penjamin = "bpjs";
                       break;
                   case R.id.rb_umum:
                       penjamin = "umum";
                       break;
               }
            }
        });
        final ArrayAdapter<String> poliAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listPoli);
        ArrayAdapter<String> dokterAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listDokter);
        poliAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPoli.setAdapter(poliAdapter);
        spinnerPoli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selected = adapterView.getItemAtPosition(position).toString();
                Context context = adapterView.getContext();
                poli = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDokter.setAdapter(dokterAdapter);
        spinnerDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selected = adapterView.getItemAtPosition(position).toString();
                Context context = adapterView.getContext();
                dokter = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HasilReservasiFragment fragment = new HasilReservasiFragment();
                Bundle arguments = new Bundle();
                arguments.putString("Date", dateFull);
                arguments.putString("Poli", poli);
                arguments.putString("Dokter", dokter);
                fragment.setArguments(arguments);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.flContent, fragment);
                ft.commit();
            }
        });
        return view;
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
                SimpleDateFormat formatFull = new SimpleDateFormat("dd / MM / yyyy");
                SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
                Date date = c.getTime();
                String dateResult = formatFull.format(date);
                String dayOfWeek = dayTranslate(formatDay.format(date));
                dateFull = dayOfWeek + ", " + dateResult;
                Toast.makeText(getActivity(), "selected date: " + dateFull,
                        Toast.LENGTH_SHORT).show();
                setDateFull(dateFull);
                etTanggal.setText(dateFull);
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
