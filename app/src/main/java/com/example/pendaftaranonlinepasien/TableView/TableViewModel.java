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

import com.example.pendaftaranonlinepasien.Activities.Admin_Menu.ListPasienActivity;
import com.example.pendaftaranonlinepasien.Activities.Data_Pasien.ListBerobatPasienActivity;
import com.example.pendaftaranonlinepasien.TableView.model.Cell;
import com.example.pendaftaranonlinepasien.TableView.model.ColumnHeader;
import com.example.pendaftaranonlinepasien.TableView.model.RowHeader;
import com.example.pendaftaranonlinepasien.Utils.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableViewModel {

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    // Constant values for icons
    public static final int SAD = 1;
    public static final int HAPPY = 2;
    public static final int BOY = 1;
    public static final int GIRL = 2;

    // Constant size for dummy data sets
    private static final int COLUMN_SIZE = 4;
    private static final int ROW_SIZE = 4;
    private Context mContext;

    public TableViewModel(Context context) {
        mContext = context;
    }

    public static List<RowHeader> GetUserRowHeaderList(List<ListPasienActivity.DataTableUser> dataTables) {
        List<RowHeader> list = new ArrayList<>();
        for (ListPasienActivity.DataTableUser data : dataTables){
            RowHeader header = new RowHeader(String.valueOf(data.getNo()), String.valueOf(data.getNo()));
            list.add(header);
        }
        return list;
    }

    public static List<RowHeader> GetBerobatRowHeaderList(List<DataTable> dataTables) {
        List<RowHeader> list = new ArrayList<>();
        for (DataTable data : dataTables){
            RowHeader header = new RowHeader(String.valueOf(data.getNo()), String.valueOf(data.getNo()));
            list.add(header);
        }
        return list;
    }

    public static List<ColumnHeader> GetUserColumnHeaderList(){
        List<ColumnHeader> list = new ArrayList<>();

        ColumnHeader header = new ColumnHeader(String.valueOf(0), "NoRM");
        list.add(header);
        ColumnHeader header1 = new ColumnHeader(String.valueOf(1), "Nama");
        list.add(header1);
        ColumnHeader header2 = new ColumnHeader(String.valueOf(1), "NIK");
        list.add(header2);

        return list;
    }

    public static List<ColumnHeader> GetBerobatColumnHeaderList(){
        List<ColumnHeader> list = new ArrayList<>();

        ColumnHeader header1 = new ColumnHeader(String.valueOf(1), "Reservasi");
        list.add(header1);
        ColumnHeader header = new ColumnHeader(String.valueOf(0), "Tanggal");
        list.add(header);
        ColumnHeader header2 = new ColumnHeader(String.valueOf(1), "Poli");
        list.add(header2);

        return list;
    }

    public static List<List<Cell>> GetUserCellList(List<ListPasienActivity.DataTableUser> dataTables, int column_size) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < dataTables.size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < column_size; j++) {
                Object data = null;
                switch (j){
                    case 0:
                        data = dataTables.get(i).getNoRM();
                        break;
                    case 1:
                        data = dataTables.get(i).getNama();
                        break;
                    case 2:
                        data = dataTables.get(i).getNik();
                        break;
                }
                Cell cell = new Cell(String.valueOf(i), data);
                cellList.add(cell);
            }
            list.add(cellList);
        }
        return list;
    }

    public static List<List<Cell>> GetBerobatCellList(List<DataTable> dataTables, int column_size) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < dataTables.size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < column_size; j++) {
                Object data = null;
                switch (j){
                    case 0:
                        data = dataTables.get(i).getReservasi();
                        break;
                    case 1:
                        data = dataTables.get(i).getTgl();
                        break;
                    case 2:
                        data = dataTables.get(i).getPoli();
                        break;
                }
                Cell cell = new Cell(String.valueOf(i), data);
                cellList.add(cell);
                }
            list.add(cellList);
            }
            return list;
    }


    private List<ColumnHeader> getSimpleColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            if (i % 6 == 2) {
                title = "large column " + i;
            } else if (i == MOOD_COLUMN_INDEX) {
                title = "mood";
            } else if (i == GENDER_COLUMN_INDEX) {
                title = "gender";
            }
            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = "column " + i;
            int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column " + i;
            }

            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }

        return list;
    }

    private List<List<Cell>> getSimpleCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();

            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                if (j % 4 == 0 && i % 5 == 0) {
                    text = "large cell " + j + " " + i + ".";
                }
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "cell " + j + " " + i;

                final int random = new Random().nextInt();
                if (j == 0) {
                    text = i;
                } else if (j == 1) {
                    text = random;
                } else if (j == MOOD_COLUMN_INDEX) {
                    text = random % 2 == 0 ? HAPPY : SAD;
                } else if (j == GENDER_COLUMN_INDEX) {
                    text = random % 2 == 0 ? BOY : GIRL;
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                if (j == 3) {
                    cell = new Cell(id, text);
                } else if (j == 4) {
                    // NOTE female and male keywords for filter will have conflict since "female"
                    // contains "male"
                    cell = new Cell(id, text);
                } else {
                    cell = new Cell(id, text);
                }
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getRandomCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + i;
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + i + getRandomString() + ".";
                }

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    private List<List<Cell>> getEmptyCellList() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell = new Cell(id, "");
                cellList.add(cell);
            }
        }

        return list;
    }

    private List<RowHeader> getEmptyRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "");
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    public static List<List<Cell>> getRandomCellList(int startIndex) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            list.add(cellList);
            for (int j = 0; j < COLUMN_SIZE; j++) {
                String text = "cell " + j + " " + (i + startIndex);
                int random = new Random().nextInt();
                if (random % 2 == 0 || random % 5 == 0 || random == j) {
                    text = "large cell  " + j + " " + (i + startIndex) + getRandomString() + ".";
                }

                String id = j + "-" + (i + startIndex);

                Cell cell = new Cell(id, text);
                cellList.add(cell);
            }
        }

        return list;
    }


    private static String getRandomString() {
        Random r = new Random();
        String str = " a ";
        for (int i = 0; i < r.nextInt(); i++) {
            str = str + " a ";
        }
        return str;
    }

}
