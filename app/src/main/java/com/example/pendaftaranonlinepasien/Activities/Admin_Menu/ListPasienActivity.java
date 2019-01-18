package com.example.pendaftaranonlinepasien.Activities.Admin_Menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.filter.Filter;
import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.TableView.TableViewAdapter;
import com.example.pendaftaranonlinepasien.TableView.TableViewListener;
import com.example.pendaftaranonlinepasien.TableView.TableViewModel;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;

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

    List<DataTableUser> listDataTableUser = new ArrayList<>();
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

        private TableViewModel mTableViewModel;
        public AbstractTableAdapter mTableViewAdapter;
        private Filter tableViewFilter;

        Unbinder unbinder;
        ContentViewHolder(View view){
            unbinder = ButterKnife.bind(this, view);
        }
        protected void unbindButterKnife(){
            unbinder.unbind();
        }

        public void initializeTableView() {
            // Create TableView View model class  to group view models of TableView
            mTableViewModel = new TableViewModel(appContext);

            // Create TableView Adapter
            mTableViewAdapter = new TableViewAdapter(appContext, mTableViewModel);

            mTableView.setAdapter(mTableViewAdapter);
            mTableView.setTableViewListener(new TableViewListener(mTableView,(TableViewAdapter)mTableViewAdapter, 0));

            // Load the dummy data to the TableView
            mTableViewAdapter.setAllItems(columnHeaderList, rowHeaderList, cellList);
            mTableView.setColumnWidth(0, 120);
            mTableView.setColumnWidth(1, 120);
            mTableView.setColumnWidth(2, 120);
            mTableView.setColumnWidth(3, 120);

            tableViewFilter = new Filter(mTableView);
        }

        public void filterTable(String filter_) {
            // Sets a filter to the table, this will filter ALL the columns.
            tableViewFilter.set(filter_);
        }
    }

    public class DataTableUser{
        int no;
        int noRM;
        String nama;
        String nik;

        public DataTableUser(int no, int noRM, String nama, String nik){
            this.no = no;
            this.noRM = noRM;
            this.nama = nama;
            this.nik= nik;
        }

        public int getNo() {
            return no;
        }
        public int getNoRM() {
            return noRM;
        }
        public String getNama() {
            return nama;
        }
        public String getNik() {
            return nik;
        }
    }

    private void initDataTable(){
        Call<ArrayList<UserPasien<Pasien>>> call = retrofitInterface.GetAllUser(api_token);
        call.enqueue(new Callback<ArrayList<UserPasien<Pasien>>>() {
            @Override
            public void onResponse(Call<ArrayList<UserPasien<Pasien>>> call, Response<ArrayList<UserPasien<Pasien>>> response) {
                if (response.body().get(0).getObject()!=null){
                    for (int i = 0; i < response.body().size(); i++){
                        Pasien pasien = response.body().get(i).getObject();
                        listDataTableUser.add(new DataTableUser(i + 1, pasien.getNorm(), pasien.getNama(), pasien.getNik()));
                    }
                    if (listDataTableUser.size() > 0){
                        rowHeaderList = TableViewModel.GetUserRowHeaderList(listDataTableUser);
                        cellList = TableViewModel.GetUserCellList(listDataTableUser, columnHeaderList.size());
                        viewHolder.progressBar.setVisibility(View.GONE);
                        viewHolder.initializeTableView();
                    }
                }
                else {
                    Toast.makeText(appContext, "Data pasien kosong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserPasien<Pasien>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_admin_list_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_4);
        this.setTitle("List Pasien");
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        columnHeaderList = TableViewModel.GetUserColumnHeaderList();
        viewHolder.etCariPasien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String _s = "";
                if(s.length()!=0)
                    _s = s.toString();
                viewHolder.filterTable(_s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initDataTable();
    }

    @Override
    public void onDestroy(){
        viewHolder.unbindButterKnife();
        super.onDestroy();
    }
}
