package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;

public class Pasien{

    @SerializedName("id")
    int id;
    @SerializedName("id_user")
    int idUser;
    @SerializedName("nama")
    String nama;
    @SerializedName("ttl")
    String ttl;
    @SerializedName("nik")
    String nik;
    @SerializedName("jk")
    char JK;
    @SerializedName("kerja")
    String kerja;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("hp")
    String hp;
    @SerializedName("ibu")
    String ibu;
    @SerializedName("role")
    String role;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;

    /**
     * ======================= Setter =============================
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public void setJK(char JK) {
        this.JK = JK;
    }
    public void setKerja(String kerja) {
        this.kerja = kerja;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setHp(String hp) {
        this.hp = hp;
    }
    public void setIbu(String ibu) {
        this.ibu = ibu;
    }
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * ==================== End of Setter ==========================
     */


    /**
     * ======================= Getter =============================
     */
    public int getId() {
        return id;
    }
    public int getIdUser() {
        return idUser;
    }
    public String getNama() {
        return nama;
    }
    public String getTtl() {
        return ttl;
    }
    public String getNik() {
        return nik;
    }
    public char getJK() {
        return JK;
    }
    public String getKerja() {
        return kerja;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getHp() {
        return hp;
    }
    public String getIbu() {
        return ibu;
    }
    public String getRole() {
        return role;
    }
    /**
     * ==================== End of Getter ==========================
     */
}
