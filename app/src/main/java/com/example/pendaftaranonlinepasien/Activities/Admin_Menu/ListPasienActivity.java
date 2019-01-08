package com.example.pendaftaranonlinepasien.Activities.Admin_Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.RiwayatPasienActivity;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.TableView.TableViewModel;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListPasienActivity extends BaseActivity {

    ContentViewHolder viewHolder;

    List<DataTable> listDataTable = new ArrayList<>();
    List<ColumnHeader> columnHeaderList = new ArrayList<>();
    List<RowHeader> rowHeaderList = new ArrayList<>();
    List<List<Cell>> cellList = new ArrayList<>();

    protected class ContentViewHolder{
        @BindView(R.id.etCariPasien)
        EditText etCariPasien;
        @BindView(R.id.pBar)
        public ProgressBar progressBar;
        @BindView(R.id.tableView_ListPasien)
        TableView mTableView;
        @BindView(R.id.btn_SearchPasien)
        Button btnSearchPasien;

        private TableViewModel mTableViewModel;
        public AbstractTableAdapter mTableViewAdapter;

        Unbinder unbinder;
        ContentViewHolder(View view){
            unbinder = ButterKnife.bind(this, view);
        }
        protected void unbindButterKnife(){
            unbinder.unbind();
        }
    }

    public class DataTable{
        int no;
        String rm;
        String nama;

        DataTable(int no, String tgl, String poli){
            this.no = no;
            this.rm = tgl;
            this.nama = poli;
        }
        public int getNo() {
            return no;
        }
        public String getRm() {
            return rm;
        }
        public String getNama() {
            return nama;
        }
    }

    private void searchPasienByNIK(String nik){
        Call<Pasien> call = retrofitInterface.SearchPasienByNIK(nik);
        call.enqueue(new Callback<Pasien>() {
            @Override
            public void onResponse(Call<Pasien> call, Response<Pasien> response) {
                if (response.body()!=null){
                    int idUser = response.body().getIdUser();
                    Intent intent = new Intent(ListPasienActivity.this, RiwayatPasienActivity.class);
                    intent.putExtra("Id", idUser);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Pasien> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void initDataTable(){
        Call<UserList<Riwayat>> call = retrofitInterface.getRiwayatPasien(SharedPreferenceUtils.getInstance(getApplicationContext()).getUserProfileValue().getId());
        call.enqueue(new Callback<UserList<Riwayat>>() {
            @Override
            public void onResponse(Call<UserList<Riwayat>> call, Response<UserList<Riwayat>> response) {
                if (response.body().getListObject()!=null){
                    for (int i = 0; i < response.body().getListObject().size(); i++){
                        Riwayat riwayatPasien = response.body().getListObject().get(i);
                        String newTgl = DataFormatConverterUtils.getInstance(getBaseContext()).convertToFormated(riwayatPasien.getTgl());
                        riwayatPasien.setTgl(newTgl);
                        listDataTable.add(new DataTable(i + 1, riwayatPasien.getTgl().toString(), riwayatPasien.getPoli()));
                    }
                    if (listDataTable.size() > 0){
                        rowHeaderList = TableViewModel.getRiwayatRowHeaderList(listDataTable);
                        cellList = TableViewModel.getRiwayatCellList(listDataTable, columnHeaderList.size());
                        viewHolder.progressBar.setVisibility(View.GONE);
                        viewHolder.initializeTableView();
                    }
                }
                else {
                    Toast.makeText(RiwayatPasienActivity.this, "Pasien belum pernah berobat!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList<Riwayat>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_list_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_2);
        //mToolbar.setTitle("Riwayat Pasien");
        //viewHolder.progressBar.setVisibility(View.VISIBLE);
        columnHeaderList = TableViewModel.getRiwayatColumnHeaderList();
        viewHolder.btnSearchPasien.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                searchPasienByNIK(viewHolder.etCariPasien.getText().toString());
            }
        });
        //initDataTable();
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
