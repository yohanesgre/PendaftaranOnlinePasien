package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;

public class Berobat {

    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    int user_id;
    @SerializedName("tgl")
    String tgl;
    @SerializedName("poli")
    String poli;
    @SerializedName("dokter")
    String dokter;
    @SerializedName("reservasi")
    String reservasi;
    @SerializedName("jam")
    String jam;
    @SerializedName("penjamin")
    String penjamin;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;

    /**
     * ======================= Setter =============================
     */
    public int getId() {
        return id;
    }
    public int getIdUser() {
        return user_id;
    }
    public String getTgl() {
        return tgl;
    }
    public String getPoli() {
        return poli;
    }
    public String getReservasi() {
        return reservasi;
    }
    public String getJam() {
        return jam;
    }
    public String getPenjamin() {
        return penjamin;
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
        this.user_id = idUser;
    }
    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
    public void setPoli(String poli) {
        this.poli = poli;
    }
    public void setReservasi(String reservasi) {
        this.reservasi = reservasi;
    }
    public void setJam(String jam) {
        this.jam = jam;
    }
    public void setDokter(String dokter) {
        this.dokter = dokter;
    }
    public void setPenjamin(String penjamin) {
        this.penjamin = penjamin;
    }
    /**
     * ==================== End of Getter ==========================
     */
}
