package com.example.pendaftaranonlinepasien.Utils;

public class DataTable{
    int no;
    String tgl;
    String reservasi;
    String poli;

    public DataTable(int no, String reservasi, String tgl,  String poli){
        this.no = no;
        this.tgl = tgl;
        this.reservasi = reservasi;
        this.poli = poli;
    }

    public String getPoli() {
        return poli;
    }
    public String getReservasi() {
        return reservasi;
    }
    public String getTgl() {
        return tgl;
    }
    public int getNo() {
        return no;
    }
}