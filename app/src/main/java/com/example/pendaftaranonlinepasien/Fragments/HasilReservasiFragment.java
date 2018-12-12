package com.example.pendaftaranonlinepasien.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pendaftaranonlinepasien.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilReservasiFragment extends Fragment {
    Context mContext;
    String date;
    String poli;
    String dokter;

    @BindView(R.id.tvTanggalBooking)
    TextView tvTanggalBooking;
    @BindView(R.id.tvPoli)
    TextView tvPoli;
    @BindView(R.id.tvDokter)
    TextView tvDokter;


    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        mContext = getActivity().getApplicationContext();
        getActivity().setTitle("Reservasi");
        Bundle arguments = getArguments();
        date = arguments.getString("Date");
        poli = arguments.getString("Poli");
        dokter = arguments.getString("Dokter");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_hasilreservasil, container, false);
        ButterKnife.bind(this, view);
        tvTanggalBooking.setText(date);
        tvPoli.setText(poli);
        tvDokter.setText(dokter);
        return view;
    }
}
