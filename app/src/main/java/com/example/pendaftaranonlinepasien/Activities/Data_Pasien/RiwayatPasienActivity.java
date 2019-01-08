package com.example.pendaftaranonlinepasien.Activities.Data_Pasien;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.Riwayat;
import com.example.pendaftaranonlinepasien.API.POJO.UserList;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.TableView.TableViewAdapter;
import com.example.pendaftaranonlinepasien.TableView.TableViewListener;
import com.example.pendaftaranonlinepasien.TableView.TableViewModel;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;
import com.example.pendaftaranonlinepasien.Utils.DataFormatConverterUtils;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatPasienActivity extends BaseActivity {

    UserObject<Pasien> user;
    UserList<Riwayat> riwayatUser;
    ContentViewHolder viewHolder;
    List<DataTable> listDataTable = new ArrayList<>();
    final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    List<ColumnHeader> columnHeaderList = new ArrayList<>();
    List<RowHeader> rowHeaderList = new ArrayList<>();
    List<List<Cell>> cellList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_riwayat_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_2);
        //mToolbar.setTitle("Riwayat Pasien");
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        user = SharedPreferenceUtils.getInstance(getApplicationContext()).getUserProfileValue();
        if (user.getObject()!=null){
            viewHolder.tvNoRM.setText(Integer.toString(user.getObject().getIdUser()));
            viewHolder.tvNama.setText(user.getObject().getNama());
        } else {
            viewHolder.tvNoRM.setText("Isi Profile terlebih dahulu!");
            viewHolder.tvNama.setText("Isi Profile terlebih dahulu!");
        }
        columnHeaderList = TableViewModel.getRiwayatColumnHeaderList();
        initDataTable();
    }

    public class ContentViewHolder {
        @BindView(R.id.pBar)
        public ProgressBar progressBar;
        @BindView(R.id.tvNoRM)
        TextView tvNoRM;
        @BindView(R.id.tvNama)
        TextView tvNama;
        @BindView(R.id.tableView_Riwayat)
        TableView mTableView;

        private TableViewModel mTableViewModel;

        public AbstractTableAdapter mTableViewAdapter;
        Unbinder unbinder;

        ContentViewHolder(View view) {
            unbinder = ButterKnife.bind(this, view);
        }
        protected void unbindButterKnife() {
            unbinder.unbind();
        }

        public void initializeTableView() {
            // Create TableView View model class  to group view models of TableView
            mTableViewModel = new TableViewModel(getApplicationContext());

            // Create TableView Adapter
            mTableViewAdapter = new TableViewAdapter(getApplicationContext(), mTableViewModel);

            mTableView.setAdapter(mTableViewAdapter);
            mTableView.setTableViewListener(new TableViewListener(mTableView));

            // Create an instance of a Filter and pass the TableView.
            //mTableFilter = new Filter(mTableView);

            // Load the dummy data to the TableView
            mTableViewAdapter.setAllItems(columnHeaderList, rowHeaderList, cellList);

            //mTableView.setHasFixedWidth(true);

        /*for (int i = 0; i < mTableViewModel.getCellList().size(); i++) {
            mTableView.setColumnWidth(i, 200);
        }*)

        //mTableView.setColumnWidth(0, -2);
        //mTableView.setColumnWidth(1, -2);

        /*mTableView.setColumnWidth(2, 200);
        mTableView.setColumnWidth(3, 300);
        mTableView.setColumnWidth(4, 400);
        mTableView.setColumnWidth(5, 500);*/
        }
    }

    public class DataTable{
        int no;
        String tgl;
        String poli;

        DataTable(int no, String tgl, String poli){
            this.no = no;
            this.tgl = tgl;
            this.poli = poli;
        }

        public String getPoli() {
            return poli;
        }
        public String getTgl() {
            return tgl;
        }
        public int getNo() {
            return no;
        }
    }

    private void initDataTable(){
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
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_riwayat_pasien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refresh:
                listDataTable.clear();
                initDataTable();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
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
