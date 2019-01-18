/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.example.pendaftaranonlinepasien.TableView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.example.pendaftaranonlinepasien.API.POJO.Berobat;
import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserBerobat;
import com.example.pendaftaranonlinepasien.API.RetrofitClient;
import com.example.pendaftaranonlinepasien.API.RetrofitInterface;
import com.example.pendaftaranonlinepasien.Activities.Admin_Menu.AdminListBerobatPasienActivity;
import com.example.pendaftaranonlinepasien.Activities.Admin_Menu.ListPasienActivity;
import com.example.pendaftaranonlinepasien.Activities.Pendaftaran_Pasien.HasilReservasiActivity;
import com.example.pendaftaranonlinepasien.TableView.holder.ColumnHeaderViewHolder;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.popup.ColumnHeaderLongPressPopup;
import com.example.pendaftaranonlinepasien.TableView.popup.RowHeaderLongPressPopup;
import com.example.pendaftaranonlinepasien.Utils.SharedPreferenceUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by evrencoskun on 21/09/2017.
 */

public class TableViewListener implements ITableViewListener {

    private Toast mToast;
    private Context mContext;
    private TableView mTableView;
    private TableViewAdapter tableViewAdapter;
    private int tableCategory;
    public RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
    public TableViewListener(TableView tableView, TableViewAdapter tableViewAdapter, int tableCategory) {
        this.tableCategory = tableCategory;
        this.tableViewAdapter = tableViewAdapter;
        this.mContext = tableView.getContext();
        this.mTableView = tableView;
    }

    /**
     * Called when user click any cell item.
     *
     * @param cellView : Clicked Cell ViewHolder.
     * @param column   : X (Column) position of Clicked Cell item.
     * @param row      : Y (Row) position of Clicked Cell item.
     */
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Cell model = tableViewAdapter.getCellItem(column, row);
        if(tableCategory==0){
            if(column==0){
                Call<Pasien> call = retrofitInterface.GetUserByNoRM(SharedPreferenceUtils.getInstance(mContext).getStringValue("api_token", ""),
                        Integer.parseInt(model.getData().toString()));
                call.enqueue(new Callback<Pasien>() {
                    @Override
                    public void onResponse(Call<Pasien> call, Response<Pasien> response) {
                        if(response.isSuccessful()){
                            Pasien newPasien = response.body();
                            Intent intent = new Intent(mContext, AdminListBerobatPasienActivity.class);
                            intent.putExtra("Profile_Pasien", newPasien);
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Pasien> call, Throwable t) {

                    }
                });
            }
        }
        if(tableCategory==1){
            if(column==0){
                Call<ArrayList<UserBerobat<Berobat>>> call = retrofitInterface.GetUserBerobatByReservasi(SharedPreferenceUtils.getInstance(mContext).getStringValue("api_token", ""),
                        model.getData().toString());
                call.enqueue(new Callback<ArrayList<UserBerobat<Berobat>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserBerobat<Berobat>>> call, Response<ArrayList<UserBerobat<Berobat>>> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(mContext, HasilReservasiActivity.class);
                            intent.putExtra("Reservasi", response.body().get(0).getListObject().get(0).getReservasi());
                            intent.putExtra("Tanggal", response.body().get(0).getListObject().get(0).getTgl());
                            intent.putExtra("Poli", response.body().get(0).getListObject().get(0).getPoli());
                            intent.putExtra("Jam", response.body().get(0).getListObject().get(0).getJam());
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserBerobat<Berobat>>> call, Throwable t) {

                    }
                });
            }
        }
    }

    /**
     * Called when user long press any cell item.
     *
     * @param cellView : Long Pressed Cell ViewHolder.
     * @param column   : X (Column) position of Long Pressed Cell item.
     * @param row      : Y (Row) position of Long Pressed Cell item.
     */
    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, final int column,
                                  int row) {
        // Do What you want
        //showToast("Cell " + column + " " + row + " has been long pressed.");
    }

    /**
     * Called when user click any column header item.
     *
     * @param columnHeaderView : Clicked Column Header ViewHolder.
     * @param column           : X (Column) position of Clicked Column Header item.
     */
    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        // Do what you want.
        //showToast("Column header  " + column + " has been clicked.");
    }

    /**
     * Called when user long press any column header item.
     *
     * @param columnHeaderView : Long Pressed Column Header ViewHolder.
     * @param column           : X (Column) position of Long Pressed Column Header item.
     */
    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
/*
        if (columnHeaderView != null && columnHeaderView instanceof ColumnHeaderViewHolder) {
            // Create Long Press Popup
            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup(
                    (ColumnHeaderViewHolder) columnHeaderView, mTableView);
            // Show
            popup.show();
        }*/
    }

    /**
     * Called when user click any Row Header item.
     *
     * @param rowHeaderView : Clicked Row Header ViewHolder.
     * @param row           : Y (Row) position of Clicked Row Header item.
     */
    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        // Do what you want.
        //showToast("Row header " + row + " has been clicked.");
    }

    /**
     * Called when user long press any row header item.
     *
     * @param rowHeaderView : Long Pressed Row Header ViewHolder.
     * @param row           : Y (Row) position of Long Pressed Row Header item.
     */
    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

        /*if (rowHeaderView != null) {
            // Create Long Press Popup
            RowHeaderLongPressPopup popup = new RowHeaderLongPressPopup(rowHeaderView, mTableView);
            // Show
            popup.show();
        }*/
    }


    private void showToast(String p_strMessage) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }

        mToast.setText(p_strMessage);
        mToast.show();
    }
}
