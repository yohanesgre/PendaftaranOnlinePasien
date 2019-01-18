package com.example.pendaftaranonlinepasien.Activities.Data_Pasien;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.BaseActivity;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.TableView.TableViewAdapter;
import com.example.pendaftaranonlinepasien.TableView.TableViewListener;
import com.example.pendaftaranonlinepasien.TableView.TableViewModel;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;
import com.example.pendaftaranonlinepasien.Utils.DataFormatConverterUtils;
import com.example.pendaftaranonlinepasien.Utils.DataTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBerobatPasienActivity extends BaseActivity {

    ContentViewHolder viewHolder;
    /** -------------- Table Properties ------------------*/
    List<DataTable> listDataTable = new ArrayList<>();
    List<ColumnHeader> columnHeaderList = new ArrayList<>();
    List<RowHeader> rowHeaderList = new ArrayList<>();
    List<List<Cell>> cellList = new ArrayList<>();
    /** ------------------------------------------------- */
    /** --------------------- Admin --------------------- */
    Pasien bundlePasien;
    /** ------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_list_berobat_pasien, frameLayout, false);
        viewHolder = new ContentViewHolder(contentView);
        frameLayout.addView(contentView);
        nvDrawer.setCheckedItem(R.id.nav_2);
        this.setTitle("Riwayat Pasien");
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        if (user.getObject() != null){
            if(user.getObject().getNorm()!=0)
                viewHolder.tvNoRM.setText(Integer.toString(user.getObject().getNorm()));
            viewHolder.tvNama.setText(user.getObject().getNama());
        } else {
            viewHolder.tvNoRM.setText("No. RM akan diisikan nanti oleh staff!");
            viewHolder.tvNama.setText("Isi Profile terlebih dahulu!");
        }
        columnHeaderList = TableViewModel.GetBerobatColumnHeaderList();
        if (user.getObject() != null){
                initDataTable();
        }else{
            viewHolder.progressBar.setVisibility(View.GONE);
            Toast.makeText(appContext, "Biodata belum terisi, isi profile terlebih dahulu!", Toast.LENGTH_LONG).show();
        }
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
            mTableViewModel = new TableViewModel(appContext);

            // Create TableView Adapter
            mTableViewAdapter = new TableViewAdapter(appContext, mTableViewModel);

            mTableView.setAdapter(mTableViewAdapter);
            mTableView.setTableViewListener(new TableViewListener(mTableView,(TableViewAdapter)mTableViewAdapter, 1));

            // Load the dummy data to the TableView
            mTableViewAdapter.setAllItems(columnHeaderList, rowHeaderList, cellList);
            mTableView.setColumnWidth(0, 200);
            mTableView.setColumnWidth(1, 200);
            mTableView.setColumnWidth(2, 200);
        }
    }

    private void initDataTable(){
            Call<ArrayList<UserBerobat<Berobat>>> call = retrofitInterface.GetUserBerobat(api_token);
            call.enqueue(new Callback<ArrayList<UserBerobat<Berobat>>>() {
                @Override
                public void onResponse(Call<ArrayList<UserBerobat<Berobat>>> call, Response<ArrayList<UserBerobat<Berobat>>> response) {
                    if (response.isSuccessful()){
                            for (int i = 0; i < response.body().get(0).getListObject().size(); i++){
                                Berobat berobatPasien = response.body().get(0).getListObject().get(i);
                                String newTgl = DataFormatConverterUtils.getInstance(getBaseContext()).convertToFormated(berobatPasien.getTgl());
                                berobatPasien.setTgl(newTgl);
                                listDataTable.add(new DataTable(i + 1, berobatPasien.getReservasi(), berobatPasien.getTgl(), berobatPasien.getPoli()));
                            }
                            if (listDataTable.size() > 0){
                                rowHeaderList = TableViewModel.GetBerobatRowHeaderList(listDataTable);
                                cellList = TableViewModel.GetBerobatCellList(listDataTable, columnHeaderList.size());
                                viewHolder.progressBar.setVisibility(View.GONE);
                                viewHolder.initializeTableView();
                            }else {
                                viewHolder.progressBar.setVisibility(View.GONE);
                                Toast.makeText(ListBerobatPasienActivity.this, "Pasien belum pernah berobat!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            viewHolder.progressBar.setVisibility(View.GONE);
                            Toast.makeText(ListBerobatPasienActivity.this, "Pasien belum pernah berobat!", Toast.LENGTH_SHORT).show();
                        }
                }

                @Override
                public void onFailure(Call<ArrayList<UserBerobat<Berobat>>> call, Throwable t) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    Toast.makeText(appContext, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    @Override
    public void onDestroy(){
        viewHolder.unbindButterKnife();
        super.onDestroy();
    }
}
