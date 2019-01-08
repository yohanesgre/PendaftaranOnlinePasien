package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;

public class Riwayat {

    @SerializedName("id")
    int id;
    @SerializedName("id_user")
    int id_user;
    @SerializedName("tgl")
    String tgl;
    @SerializedName("poli")
    String poli;
    @SerializedName("reservasi")
    int reservasi;
    @SerializedName("jam")
    String jam;
    @SerializedName("nmr")
    int nmr;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;

    /**
     * ======================= Setter =============================
     */
    public int getId() {
        return id;
    }
    public int getIdUser() {
        return id_user;
    }
    public String getTgl() {
        return tgl;
    }
    public String getPoli() {
        return poli;
    }
    public int getReservasi() {
        return reservasi;
    }
    public String getJam() {
        return jam;
    }
    public int getNmr() {
        return nmr;
    }
    /**
     * ==================== End of Setter ==========================
     */


    /**
     * ======================= Getter =============================
     */
    public void setId(int id) {
        this.id = id;
    }
    public void setIdUser(int idUser) {
        this.id_user = idUser;
    }
    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
    public void setPoli(String poli) {
        this.poli = poli;
    }
    public void setReservasi(int reservasi) {
        this.reservasi = reservasi;
    }
    public void setJam(String jam) {
        this.jam = jam;
    }
    public void setNmr(int nmr) {
        this.nmr = nmr;
    }
    /**
     * ==================== End of Getter ==========================
     */
}
