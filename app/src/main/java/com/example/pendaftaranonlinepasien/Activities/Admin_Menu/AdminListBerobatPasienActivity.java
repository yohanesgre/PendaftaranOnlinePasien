package com.example.pendaftaranonlinepasien.Activities.Admin_Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.API.POJO.UserPasien;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.R;
import com.example.pendaftaranonlinepasien.TableView.TableViewAdapter;
import com.example.pendaftaranonlinepasien.TableView.TableViewListener;
import com.example.pendaftaranonlinepasien.TableView.TableViewModel;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;
import com.example.pendaftaranonlinepasien.Utils.DataFormatConverterUtils;
import com.example.pendaftaranonlinepasien.Utils.DataTable;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListBerobatPasienActivity extends AppCompatActivity {
    private RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    private UserPasien<Pasien> user;
    private Context appContext;
    private String api_token;private TableViewModel mTableViewModel;
    private AbstractTableAdapter mTableViewAdapter;

    @BindView(R.id.pBar)
    public ProgressBar progressBar;
    @BindView(R.id.tvNoRM)
    TextView tvNoRM;
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.tableView_Riwayat)
    TableView mTableView;

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
        setContentView(R.layout.activity_admin_list_berobat_pasien);
        ButterKnife.bind(this);
        appContext = getApplicationContext();
        api_token = SharedPreferenceUtils.getInstance(appContext).getStringValue("api_token", "");
        if (SharedPreferenceUtils.getInstance(appContext).getUserProfileValue() != null)
            user = SharedPreferenceUtils.getInstance(appContext).getUserProfileValue();
        progressBar.setVisibility(View.VISIBLE);
        if (user.getObject() != null){
            tvNoRM.setText(Integer.toString(user.getObject().getIdUser()));
            tvNama.setText(user.getObject().getNama());
        } else {
            tvNoRM.setText("Isi Profile terlebih dahulu!");
            tvNama.setText("Isi Profile terlebih dahulu!");
        }
        columnHeaderList = TableViewModel.GetBerobatColumnHeaderList();

        if (getIntent().getSerializableExtra("Profile_Pasien") != null) {
            bundlePasien = (Pasien) getIntent().getSerializableExtra("Profile_Pasien");
            initDataTable(bundlePasien.getIdUser());
            tvNama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminListBerobatPasienActivity.this, AdminDataPasienActivity.class);
                    intent.putExtra("user_id", bundlePasien);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 1);
                }
            });
        }else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(appContext, "Biodata pasien error!", Toast.LENGTH_LONG).show();
        }
    }

        public void initializeTableView() {
            // Create TableView View model class  to group view models of TableView
            mTableViewModel = new TableViewModel(appContext);

            // Create TableView Adapter
            mTableViewAdapter = new TableViewAdapter(appContext, mTableViewModel);

            mTableView.setAdapter(mTableViewAdapter);
            mTableView.setTableViewListener(new TableViewListener(mTableView, (TableViewAdapter)mTableViewAdapter, 1));

            // Load the dummy data to the TableView
            mTableViewAdapter.setAllItems(columnHeaderList, rowHeaderList, cellList);

            mTableView.setColumnWidth(0, 200);
            mTableView.setColumnWidth(1, 200);
            mTableView.setColumnWidth(2, 200);
        }

    private void initDataTable(int user_id){
            Call<ArrayList<UserBerobat<Berobat>>> call = retrofitInterface.GetUserBerobatByID(api_token, user_id);
            call.enqueue(new Callback<ArrayList<UserBerobat<Berobat>>>() {
                @Override
                public void onResponse(Call<ArrayList<UserBerobat<Berobat>>> call, Response<ArrayList<UserBerobat<Berobat>>> response) {
                    if (response.isSuccessful()){
                        if (response.body().get(0).getListObject()!=null){
                            for (int i = 0; i < response.body().get(0).getListObject().size(); i++){
                                Berobat berobatPasien = response.body().get(0).getListObject().get(i);
                                String newTgl = DataFormatConverterUtils.getInstance(getBaseContext()).convertToFormated(berobatPasien.getTgl());
                                berobatPasien.setTgl(newTgl);
                                listDataTable.add(new DataTable(i + 1, berobatPasien.getReservasi(), berobatPasien.getTgl() ,berobatPasien.getPoli()));
                            }
                            if (listDataTable.size() > 0){
                                rowHeaderList = TableViewModel.GetBerobatRowHeaderList(listDataTable);
                                cellList = TableViewModel.GetBerobatCellList(listDataTable, columnHeaderList.size());
                                progressBar.setVisibility(View.GONE);
                                initializeTableView();
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AdminListBerobatPasienActivity.this, "Pasien belum pernah berobat!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminListBerobatPasienActivity.this, "Pasien belum pernah berobat!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<UserBerobat<Berobat>>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(appContext, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void onBackPressed() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finish();
            } else {
                super.onBackPressed();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                tvNama.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
