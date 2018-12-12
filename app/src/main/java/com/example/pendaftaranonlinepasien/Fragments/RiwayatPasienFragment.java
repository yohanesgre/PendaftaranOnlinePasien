package com.example.pendaftaranonlinepasien.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pendaftaranonlinepasien.R;

import butterknife.ButterKnife;

public class RiwayatPasienFragment extends Fragment {

    Context mContext;


    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        getActivity().setTitle("Riwayat Pasien");
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caririwayat, container, false);
        ButterKnife.bind(this, view);
        /*TableView tableView = new TableView(getContext());
        // Create our custom TableView Adapter
        PasienTableViewAdapter adapter = new PasienTableViewAdapter(getContext());

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        // Let's set datas of the TableView on the Adapter
        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
        */return view;
    }

}
